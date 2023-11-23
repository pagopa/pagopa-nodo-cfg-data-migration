package it.gov.pagopa.nodo.datamigration.fsm;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.BinaryFile;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.InvalidMigrationStatusException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.step.ExecuteBinaryFileTableMigrationStep;
import it.gov.pagopa.nodo.datamigration.repository.oracle.BinaryFileSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.BinaryFileDestRepository;
import it.gov.pagopa.nodo.datamigration.repository.h2.CfgDataMigrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StepTest {

    @InjectMocks
    @Spy
    private ExecuteBinaryFileTableMigrationStep step;

    @Mock
    private BinaryFileSrcRepository srcRepo;

    @Mock
    private BinaryFileDestRepository destRepo;

    @Mock
    private CfgDataMigrationRepository cfgDataMigrationRepository;

    @Mock
    private FSMSharedState fsmSharedState;

    @Mock
    private DataMigration dataMigration;

    @Mock
    private DataMigrationDetails dataMigrationDetails;

    @Mock
    private DataMigrationStatus dataMigrationStatus;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        dataMigrationStatus = new DataMigrationStatus();
        dataMigrationDetails = new DataMigrationDetails();
        dataMigration = new DataMigration();

        Field statusField = DataMigrationDetails.class.getDeclaredField("binaryFile");
        statusField.setAccessible(true);
        statusField.set(dataMigrationDetails, dataMigrationStatus);

        Field detailsField = DataMigration.class.getDeclaredField("details");
        detailsField.setAccessible(true);
        detailsField.set(dataMigration, dataMigrationDetails);

        Field pageSize = step.getClass().getDeclaredField("PAGE_SIZE");
        pageSize.setAccessible(true);
        pageSize.set(step, 1);

        lenient().when(cfgDataMigrationRepository.findById(any())).thenReturn(Optional.of(dataMigration));
        step.attachSharedState(fsmSharedState, cfgDataMigrationRepository);
    }

    @Test
    void testExecuteStep() {
        when(srcRepo.findAll(any(Pageable.class))).thenReturn(createMockPage());

        assertDoesNotThrow(() -> step.executeStep());
    }

    @Test
    void testExecuteStepDataAccessException() {
        when(srcRepo.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Test Exception") {});

        assertThrows(MigrationStepException.class, () -> step.executeStep());
    }

    @Test
    void testCall() {
        when(srcRepo.findAll(any(Pageable.class))).thenReturn(createMockPage());

        StepName result = step.call();

        assertNotNull(result);
    }

    @Test
    void testCallMigrationInterruptedStepException() throws MigrationStepException {
        doThrow(new MigrationInterruptedStepException())
                .when(step)
                .checkExecutionBlock(cfgDataMigrationRepository, true);

        StepName result = step.call();

        assertNotNull(result);
        assertEquals(StepName.END, result);
    }

    @Test
    void testCallMigrationStepException() throws MigrationStepException {
        doThrow(new MigrationStepException())
                .when(step)
                .executeStep();

        StepName result = step.call();

        assertNotNull(result);
        assertEquals(StepName.ERROR, result);
    }

    @Test
    void testCanContinueReadPages() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        when(fsmSharedState.isBlockRequested()).thenReturn(false);
        when(fsmSharedState.isInLock()).thenReturn(true);

        Pageable pageable = PageRequest.of(0, 1);

        Method method = Step.class.getDeclaredMethod("canContinueReadPages", Pageable.class);
        method.setAccessible(true);

        boolean result = (boolean) method.invoke(step, pageable);

        assertTrue(result);
    }

    @Test
    void testCheckExecutionBlock() throws NoSuchMethodException {
        when(fsmSharedState.isBlockRequested()).thenReturn(false);

        Method method = Step.class.getDeclaredMethod(
                "checkExecutionBlock", CfgDataMigrationRepository.class, boolean.class);
        method.setAccessible(true);

        assertDoesNotThrow(() -> {
            method.invoke(step, cfgDataMigrationRepository, true);
        });
    }

    @Test
    void testCheckExecutionBlockBlockRequested() throws InvalidMigrationStatusException {
        when(fsmSharedState.isBlockRequested()).thenReturn(true);
        doNothing().when(step).updateDataMigrationStatusOnStart(any(cfgDataMigrationRepository.getClass()));
        when(cfgDataMigrationRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(InvalidMigrationStatusException.class, () -> step.executeStep());
    }

    private Page<BinaryFile> createMockPage() {
        BinaryFile binaryFile = new BinaryFile();
        List<BinaryFile> content = Collections.singletonList(binaryFile);
        return new PageImpl<>(content, PageRequest.of(0, 1), 1);
    }
}
