package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.nodo.datamigration.repository.oracle.OracleDBSystemRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.PostgresDBSystemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = EndStep.class)
class EndStepTest {

    @InjectMocks
    private EndStep endStep;

    @MockBean
    private OracleDBSystemRepository oracleSystemRepo;

    @MockBean
    private PostgresDBSystemRepository postgresSystemRepo;

    @MockBean
    private CfgDataMigrationRepository cfgDataMigrationRepository;

    @Mock
    private DataMigration dataMigration;

    @Mock
    private FSMSharedState fsmSharedState;

    @Test
    void testExecuteStep() {
        when(cfgDataMigrationRepository.findById(any())).thenReturn(Optional.of(dataMigration));
        when(oracleSystemRepo.readHibernateSequence()).thenReturn(1L);
        when(cfgDataMigrationRepository.saveAndFlush(any(DataMigration.class))).thenReturn(dataMigration);

        endStep.executeStep();

        verify(dataMigration).setStatus(anyString());
        verify(dataMigration).setEnd(any());
        verify(cfgDataMigrationRepository).saveAndFlush(any(DataMigration.class));
    }

    @Test
    void testExecuteStepEmptyDatamigration() {
        when(cfgDataMigrationRepository.findById(anyString())).thenReturn(Optional.empty());

        endStep.executeStep();

        verify(dataMigration, times(0)).setStatus(anyString());
        verify(dataMigration, times(0)).setEnd(any());
        verify(cfgDataMigrationRepository, times(0)).saveAndFlush(any(DataMigration.class));
    }

    @Test
    void testExecuteStepExceptionThrown() {
        when(cfgDataMigrationRepository.findById(anyString())).thenThrow(new DataAccessException("Test Exception") {});

        verify(dataMigration, times(0)).setStatus(anyString());
        verify(dataMigration, times(0)).setEnd(any());
        verify(cfgDataMigrationRepository, times(0)).saveAndFlush(any(DataMigration.class));
    }

    @Test
    void testUpdateSequenceLastValueExceptionThrown() {
        when(oracleSystemRepo.readHibernateSequence()).thenThrow(new RuntimeException("Test exception"));

        verify(postgresSystemRepo, times(0)).updateHibernateSequence(-1L);
    }

    @Test
    void getDataMigrationStatus() {
        DataMigrationStatus dataMigrationStatus = endStep.getDataMigrationStatus(new DataMigrationDetails());
        assertNull(dataMigrationStatus);
    }

    @Test
    void getNextState() {
        StepName nextState = endStep.getNextState();
        assertNull(nextState);
    }

    @Test
    void getStepName() {
        String stepName = endStep.getStepName();
        assertEquals("END", stepName);
    }
}
