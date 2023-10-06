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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = HealthCheckService.class)
class StartStepTest {

    @InjectMocks
    private StartStep startStep;

    @MockBean private BinaryFileDestRepository binaryFileRepo;
    @MockBean private CanaleTipoVersamentoDestRepository canaleTipoVersamentoRepo;
    @MockBean private CanaliDestRepository canaliRepo;
    @MockBean private CanaliNodoDestRepository canaliNodoRepo;
    @MockBean private CdiDetailDestRepository cdiDetailRepo;
    @MockBean private CdiFasciaCostoServizioDestRepository cdiFasciaCostoServizioRepo;
    @MockBean private CdiInformazioniServizioDestRepository cdiInformazioniServizioRepo;
    @MockBean private CdiMasterDestRepository cdiMasterRepo;
    @MockBean private CdiPreferenceDestRepository cdiPreferenceRepo;
    @MockBean private CdsCategoriaDestRepository cdsCategoriaRepo;
    @MockBean private CdsServizioDestRepository cdsServizioRepo;
    @MockBean private CdsSoggettoDestRepository cdsSoggettoRepo;
    @MockBean private CdsSoggettoServizioDestRepository cdsSoggettoServizioRepo;
    @MockBean private CodificheDestRepository codificheRepo;
    @MockBean private CodifichePaDestRepository codifichePaRepo;
    @MockBean private ConfigurationKeysDestRepository configurationKeysRepo;
    @MockBean private DizionarioMetadatiDestRepository dizionarioMetadatiRepo;
    @MockBean private ElencoServiziDestRepository elencoServiziRepo;
    @MockBean private FtpServersDestRepository ftpServersRepo;
    @MockBean private GdeConfigDestRepository gdeConfigRepo;
    @MockBean private InformativeContoAccreditoDetailDestRepository informativeContoAccreditoDetailRepo;
    @MockBean private InformativeContoAccreditoMasterDestRepository informativeContoAccreditoMasterRepo;
    @MockBean private InformativePaDetailDestRepository informativePaDetailRepo;
    @MockBean private InformativePaFasceDestRepository informativePaFasceRepo;
    @MockBean private InformativePaMasterDestRepository informativePaMasterRepo;
    @MockBean private IntermediariPaDestRepository intermediariPaRepo;
    @MockBean private IntermediariPspDestRepository intermediariPspRepo;
    @MockBean private PaDestRepository paRepo;
    @MockBean private PaStazionePaDestRepository paStazionePaRepo;
    @MockBean private PddDestRepository pddRepo;
    @MockBean private PspCanaleTipoVersamentoDestRepository pspCanaleTipoVersamentoRepo;
    @MockBean private PspDestRepository pspRepo;
    @MockBean private QuadratureSchedDestRepository quadratureSchedRepo;
    @MockBean private StazioniDestRepository stazioniRepo;
    @MockBean private TipiVersamentoDestRepository tipiVersamentoRepo;
    @MockBean private WfespPluginConfDestRepository wfespPluginConfRepo;

    @MockBean private CfgDataMigrationRepository dataMigrationRepository;
    @MockBean private HealthCheckService healthCheckService;

    @Mock private FSMSharedState fsmSharedState;

    @Test
    void testExecuteStep() throws MigrationStepException, IllegalAccessException, NoSuchFieldException {
        when(healthCheckService.getHealthCheckForOracleDB()).thenReturn(true);
        when(healthCheckService.getHealthCheckForPostgresDB()).thenReturn(true);

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

        doThrow(new DataAccessException("Test Exception") {}).when(binaryFileRepo).deleteAll();

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
        assert dataMigrationStatus == null;
    }

    @Test
    void getNextState() {
        StepName nextState = startStep.getNextState();
        assert nextState == StepName.EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION;
    }

    @Test
    void getStepName() {
        String stepName = startStep.getStepName();
        assert stepName.equals("START");
    }
}
