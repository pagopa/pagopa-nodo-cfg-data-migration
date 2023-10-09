package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.DatabaseConnectionException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStatusSavingException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationTruncateAllTablesException;
import it.gov.pagopa.nodo.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.nodo.datamigration.repository.postgres.*;
import it.gov.pagopa.nodo.datamigration.service.HealthCheckService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = HealthCheckService.class)
class StartStepTest {

    @InjectMocks
    private StartStep startStep;

    @MockBean BinaryFileDestRepository binaryFileRepo;
    @MockBean CanaleTipoVersamentoDestRepository canaleTipoVersamentoRepo;
    @MockBean CanaliDestRepository canaliRepo;
    @MockBean CanaliNodoDestRepository canaliNodoRepo;
    @MockBean CdiDetailDestRepository cdiDetailRepo;
    @MockBean CdiFasciaCostoServizioDestRepository cdiFasciaCostoServizioRepo;
    @MockBean CdiInformazioniServizioDestRepository cdiInformazioniServizioRepo;
    @MockBean CdiMasterDestRepository cdiMasterRepo;
    @MockBean CdiPreferenceDestRepository cdiPreferenceRepo;
    @MockBean CdsCategoriaDestRepository cdsCategoriaRepo;
    @MockBean CdsServizioDestRepository cdsServizioRepo;
    @MockBean CdsSoggettoDestRepository cdsSoggettoRepo;
    @MockBean CdsSoggettoServizioDestRepository cdsSoggettoServizioRepo;
    @MockBean CodificheDestRepository codificheRepo;
    @MockBean CodifichePaDestRepository codifichePaRepo;
    @MockBean ConfigurationKeysDestRepository configurationKeysRepo;
    @MockBean DizionarioMetadatiDestRepository dizionarioMetadatiRepo;
    @MockBean ElencoServiziDestRepository elencoServiziRepo;
    @MockBean FtpServersDestRepository ftpServersRepo;
    @MockBean GdeConfigDestRepository gdeConfigRepo;
    @MockBean InformativeContoAccreditoDetailDestRepository informativeContoAccreditoDetailRepo;
    @MockBean InformativeContoAccreditoMasterDestRepository informativeContoAccreditoMasterRepo;
    @MockBean InformativePaDetailDestRepository informativePaDetailRepo;
    @MockBean InformativePaFasceDestRepository informativePaFasceRepo;
    @MockBean InformativePaMasterDestRepository informativePaMasterRepo;
    @MockBean IntermediariPaDestRepository intermediariPaRepo;
    @MockBean IntermediariPspDestRepository intermediariPspRepo;
    @MockBean PaDestRepository paRepo;
    @MockBean PaStazionePaDestRepository paStazionePaRepo;
    @MockBean PddDestRepository pddRepo;
    @MockBean PspCanaleTipoVersamentoDestRepository pspCanaleTipoVersamentoRepo;
    @MockBean PspDestRepository pspRepo;
    @MockBean QuadratureSchedDestRepository quadratureSchedRepo;
    @MockBean StazioniDestRepository stazioniRepo;
    @MockBean TipiVersamentoDestRepository tipiVersamentoRepo;
    @MockBean WfespPluginConfDestRepository wfespPluginConfRepo;

    @MockBean CfgDataMigrationRepository dataMigrationRepository;
    @MockBean HealthCheckService healthCheckService;

    @Mock private EntityManagerFactory emFactory;
    @Mock private EntityManager em;
    @Mock private Query query;
    @Mock private FSMSharedState fsmSharedState;

    @Test
    void testExecuteStep() throws MigrationStepException, IllegalAccessException, NoSuchFieldException {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(true);
        when(healthCheckService.getHealthCheckForPostgresDB()).thenReturn(true);
        when(emFactory.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(em.createNativeQuery(anyString())).thenReturn(query);

        startStep.executeStep();

        verify(healthCheckService).getHealthCheckForOracleDB();
        verify(healthCheckService).getHealthCheckForPostgresDB();
    }

    @Test
    void testExecuteStepDatabaseConnectionExceptionExceptionOracleDB() {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(false);

        assertThrows(DatabaseConnectionException.class, () -> startStep.executeStep());

        verify(healthCheckService).getHealthCheckForOracleDB();
    }

    @Test
    void testExecuteStepDatabaseConnectionExceptionPostgresDB() {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(true);
        when(healthCheckService.getHealthCheckForPostgresDB()).thenReturn(false);

        assertThrows(DatabaseConnectionException.class, () -> startStep.executeStep());

        verify(healthCheckService).getHealthCheckForOracleDB();
        verify(healthCheckService).getHealthCheckForPostgresDB();
    }

    @Test
    void testExecuteStepMigrationTruncateAllTablesException() {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(true);
        when(healthCheckService.getHealthCheckForPostgresDB()).thenReturn(true);
        when(emFactory.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(mock(EntityTransaction.class));

        doThrow(new DataAccessException("Test Exception") {}).when(em).createNativeQuery(anyString());

        assertThrows(MigrationTruncateAllTablesException.class, () -> startStep.executeStep());

        verify(healthCheckService).getHealthCheckForOracleDB();
        verify(healthCheckService).getHealthCheckForPostgresDB();
    }

    @Test
    void testExecuteStepMigrationStatusSavingException() {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(true);
        when(healthCheckService.getHealthCheckForPostgresDB()).thenReturn(true);

        doThrow(new DataAccessException("Test Exception") {}).when(dataMigrationRepository)
                .save(any(DataMigration.class));

        assertThrows(MigrationStatusSavingException.class, () -> startStep.executeStep());

        verify(healthCheckService).getHealthCheckForOracleDB();
        verify(healthCheckService).getHealthCheckForPostgresDB();
    }

    @Test
    void getDataMigrationStatus() {
        DataMigrationStatus dataMigrationStatus = startStep.getDataMigrationStatus(new DataMigrationDetails());
        assertNull(dataMigrationStatus);
    }

    @Test
    void getNextState() {
        StepName nextState = startStep.getNextState();
        assertEquals(StepName.EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION, nextState);
    }

    @Test
    void getStepName() {
        String stepName = startStep.getStepName();
        assertEquals("START", stepName);
    }
}
