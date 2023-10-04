package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.InformativeContoAccreditoDetail;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.nodo.datamigration.repository.oracle.InformativeContoAccreditoDetailSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.InformativeContoAccreditoDetailDestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ExecuteInformativeContoAccreditoDetailTableMigrationStep.class)
class ExecuteInformativeContoAccreditoDetailTableMigrationStepTest {

    @MockBean
    InformativeContoAccreditoDetailSrcRepository srcRepo;

    @MockBean
    InformativeContoAccreditoDetailDestRepository destRepo;

    @MockBean
    CfgDataMigrationRepository dataMigrationRepository;

    @InjectMocks
    @Autowired
    @Spy
    ExecuteInformativeContoAccreditoDetailTableMigrationStep migrationStep;

    @Mock
    FSMSharedState sharedState;

    DataMigration dataMigration;

    DataMigrationDetails dataMigrationDetails;

    DataMigrationStatus dataMigrationStatus;

    static int pageSize;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        dataMigrationStatus = new DataMigrationStatus();
        dataMigrationDetails = new DataMigrationDetails();
        dataMigration = new DataMigration();

        Field statusField = DataMigrationDetails.class.getDeclaredField("informativeContoAccreditoDetail");
        statusField.setAccessible(true);
        statusField.set(dataMigrationDetails, dataMigrationStatus);

        Field detailsField = DataMigration.class.getDeclaredField("details");
        detailsField.setAccessible(true);
        detailsField.set(dataMigration, dataMigrationDetails);

        when(dataMigrationRepository.findById("1")).thenReturn(Optional.of(dataMigration));
    }

    @Test
    void testExecuteStep() throws MigrationStepException, IllegalAccessException, NoSuchFieldException {
        InformativeContoAccreditoDetail informativeContoAccreditoDetail = new InformativeContoAccreditoDetail();
        Page<InformativeContoAccreditoDetail> pagedResponse = new PageImpl<>(Collections.singletonList(informativeContoAccreditoDetail));

        // Emulating findAll
        Field field = ExecuteInformativeContoAccreditoDetailTableMigrationStep.class.getDeclaredField("PAGE_SIZE");
        field.setAccessible(true);
        pageSize = (int) field.get(null);
        Pageable pageable = PageRequest.of(0, pageSize);
        when(srcRepo.findAll(pageable)).thenReturn(pagedResponse);

        when(sharedState.getDataMigrationStateId()).thenReturn("1");
        when(dataMigrationRepository.findById(anyString())).thenReturn(Optional.of(dataMigration));

        migrationStep.executeStep();

        verify(srcRepo, times(1)).findAll(any(Pageable.class));
        verify(destRepo, times(1)).saveAllAndFlush(anyList());
    }

    @Test
    void testExecuteStepMigrationErrorOnStepException() throws MigrationStepException {
        Pageable pageable = PageRequest.of(0, pageSize);
        when(srcRepo.findAll(pageable)).thenThrow(new DataAccessException("Test Exception") {});
        when(sharedState.getDataMigrationStateId()).thenReturn("1");

        assertThrows(MigrationErrorOnStepException.class, () -> migrationStep.executeStep());

        verify(migrationStep, times(1)).updateDataMigrationStatusOnFailure(any());
    }

    @Test
    void testExecuteStepMigrationInterruptedStepException() throws MigrationStepException {
        when(sharedState.isBlockRequested()).thenReturn(true);
        when(sharedState.getDataMigrationStateId()).thenReturn("1");

        assertThrows(MigrationInterruptedStepException.class, () -> migrationStep.executeStep());

        verify(migrationStep, times(1)).updateDataMigrationStatusOnBlock(any());
    }

    @Test
    void getNextState() {
        StepName nextState = migrationStep.getNextState();
        assert nextState == StepName.EXECUTE_INFORMATIVE_PA_MASTER_TABLE_MIGRATION;
    }

    @Test
    void getStepName() {
        String stepName = migrationStep.getStepName();
        assert stepName.equals("EXECUTE_INFORMATIVE_CONTO_ACCREDITO_DETAIL_TABLE_MIGRATION");
    }
}
