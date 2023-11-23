package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.InformativeContoAccreditoMaster;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.nodo.datamigration.repository.oracle.InformativeContoAccreditoMasterSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.InformativeContoAccreditoMasterDestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ExecuteInformativeContoAccreditoMasterTableMigrationStep.class)
class ExecuteInformativeContoAccreditoMasterTableMigrationStepTest {

    @MockBean
    InformativeContoAccreditoMasterSrcRepository srcRepo;

    @MockBean
    InformativeContoAccreditoMasterDestRepository destRepo;

    @MockBean
    CfgDataMigrationRepository dataMigrationRepository;

    @InjectMocks
    @Autowired
    @Spy
    ExecuteInformativeContoAccreditoMasterTableMigrationStep migrationStep;

    @Mock
    FSMSharedState sharedState;

    DataMigration dataMigration;

    DataMigrationDetails dataMigrationDetails;

    DataMigrationStatus dataMigrationStatus;

    @Value("${step.informative_conto_accredito_master.batch.size}")
    int PAGE_SIZE;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        dataMigrationStatus = new DataMigrationStatus();
        dataMigrationDetails = new DataMigrationDetails();
        dataMigration = new DataMigration();

        Field statusField = DataMigrationDetails.class.getDeclaredField("informativeContoAccreditoMaster");
        statusField.setAccessible(true);
        statusField.set(dataMigrationDetails, dataMigrationStatus);

        Field detailsField = DataMigration.class.getDeclaredField("details");
        detailsField.setAccessible(true);
        detailsField.set(dataMigration, dataMigrationDetails);

        when(dataMigrationRepository.findById("1")).thenReturn(Optional.of(dataMigration));
    }

    @Test
    void testExecuteStep() throws MigrationStepException, IllegalAccessException, NoSuchFieldException {
        InformativeContoAccreditoMaster informativeContoAccreditoMaster = new InformativeContoAccreditoMaster();
        Page<InformativeContoAccreditoMaster> pagedResponse = new PageImpl<>(Collections.singletonList(informativeContoAccreditoMaster));

        // Emulating findAll
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        when(srcRepo.findAll(pageable)).thenReturn(pagedResponse);

        when(sharedState.getDataMigrationStateId()).thenReturn("1");
        when(dataMigrationRepository.findById(anyString())).thenReturn(Optional.of(dataMigration));

        migrationStep.executeStep();

        verify(srcRepo, times(1)).findAll(any(Pageable.class));
        verify(destRepo, times(1)).saveAllAndFlush(anyList());
    }

    @Test
    void testExecuteStepMigrationErrorOnStepException() throws MigrationStepException {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
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
        assertEquals(StepName.EXECUTE_INFORMATIVE_CONTO_ACCREDITO_DETAIL_TABLE_MIGRATION, nextState);
    }

    @Test
    void getStepName() {
        String stepName = migrationStep.getStepName();
        assertEquals("EXECUTE_INFORMATIVE_CONTO_ACCREDITO_MASTER_TABLE_MIGRATION", stepName);
    }
}