package it.gov.pagopa.nodo.datamigration.service;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.fsm.FSMExecutor;
import it.gov.pagopa.nodo.datamigration.model.migration.MigrationStatus;
import it.gov.pagopa.nodo.datamigration.model.migration.TableMigrationStatus;
import it.gov.pagopa.nodo.datamigration.repository.h2.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MigrationServiceTest {
    @Mock
    private DataMigration dataMigration;

    @Mock
    private FSMExecutor fsmExecutor;

    @Mock
    private CfgDataMigrationRepository cfgDataMigrationRepository;

    @InjectMocks
    private MigrationService migrationService;

    @Test
    public void testStartMigration() {
        migrationService.startMigration();
        verify(fsmExecutor).start();
    }

    @Test
    public void testReStartMigration() {
        StepName lastExecutedName = StepName.EXECUTE_BINARY_FILE_TABLE_MIGRATION;
        when(fsmExecutor.restart()).thenReturn(lastExecutedName);

        migrationService.reStartMigration();

        verify(fsmExecutor).restart();
        verify(fsmExecutor).start(lastExecutedName);
    }

    @Test
    public void testForcedStopMigration() {
        migrationService.forcedStopMigration();

        verify(fsmExecutor).forceStop();
    }

    @Test
    public void testGetMigrationStatus() {
        DataMigrationDetails dataMigrationDetails = DataMigrationDetails.builder()
                .intermediariPa(new DataMigrationStatus())
                .pa(new DataMigrationStatus())
                .stazioni(new DataMigrationStatus())
                .paStazioniPa(new DataMigrationStatus())
                .codifiche(new DataMigrationStatus())
                .codifichePa(new DataMigrationStatus())
                .binaryFile(new DataMigrationStatus())
                .informativeContoAccreditoMaster(new DataMigrationStatus())
                .informativeContoAccreditoDetail(new DataMigrationStatus())
                .informativePaMaster(new DataMigrationStatus())
                .informativePaDetail(new DataMigrationStatus())
                .informativePaFasce(new DataMigrationStatus())
                .intermediariPsp(new DataMigrationStatus())
                .psp(new DataMigrationStatus())
                .canaliNodo(new DataMigrationStatus())
                .canali(new DataMigrationStatus())
                .tipiVersamento(new DataMigrationStatus())
                .canaleTipoVersamento(new DataMigrationStatus())
                .pspCanaleTipoVersamento(new DataMigrationStatus())
                .dizionarioMetadati(new DataMigrationStatus())
                .cdiMaster(new DataMigrationStatus())
                .cdiDetail(new DataMigrationStatus())
                .cdiFasciaCostoServizio(new DataMigrationStatus())
                .cdiInformazioniServizio(new DataMigrationStatus())
                .cdiPreferences(new DataMigrationStatus())
                .elencoServizi(new DataMigrationStatus())
                .cdsCategorie(new DataMigrationStatus())
                .cdsSoggetto(new DataMigrationStatus())
                .cdsServizio(new DataMigrationStatus())
                .cdsSoggettoServizio(new DataMigrationStatus())
                .configurationKeys(new DataMigrationStatus())
                .wfespPluginConf(new DataMigrationStatus())
                .ftpServers(new DataMigrationStatus())
                .pdd(new DataMigrationStatus())
                .gdeConfig(new DataMigrationStatus())
                .quadratureSched(new DataMigrationStatus())
                .build();

        DataMigration dataMigration = new DataMigration();
        dataMigration.setDetails(dataMigrationDetails);

        Timestamp now = CommonUtils.now();
        dataMigration.setStart(now);

        Map<String, TableMigrationStatus> details = new HashMap<>();
        details.put("INTERMEDIARI_PA", getTableMigrationStatus(dataMigrationDetails.getIntermediariPa()));
        details.put("PA", getTableMigrationStatus(dataMigrationDetails.getPa()));
        details.put("STAZIONI", getTableMigrationStatus(dataMigrationDetails.getStazioni()));
        details.put("PA_STAZIONE_PA", getTableMigrationStatus(dataMigrationDetails.getPaStazioniPa()));
        details.put("CODIFICHE", getTableMigrationStatus(dataMigrationDetails.getCodifiche()));
        details.put("CODIFICHE_PA", getTableMigrationStatus(dataMigrationDetails.getCodifichePa()));
        details.put("BINARY_FILE", getTableMigrationStatus(dataMigrationDetails.getBinaryFile()));
        details.put("INFORMATIVE_CONTO_ACCREDITO_MASTER", getTableMigrationStatus(dataMigrationDetails.getInformativeContoAccreditoMaster()));
        details.put("INFORMATIVE_CONTO_ACCREDITO_DETAIL", getTableMigrationStatus(dataMigrationDetails.getInformativeContoAccreditoDetail()));
        details.put("INFORMATIVE_PA_MASTER", getTableMigrationStatus(dataMigrationDetails.getInformativePaMaster()));
        details.put("INFORMATIVE_PA_DETAIL", getTableMigrationStatus(dataMigrationDetails.getInformativePaDetail()));
        details.put("INFORMATIVE_PA_FASCE", getTableMigrationStatus(dataMigrationDetails.getInformativePaFasce()));
        details.put("INTERMEDIARI_PSP", getTableMigrationStatus(dataMigrationDetails.getIntermediariPsp()));
        details.put("PSP", getTableMigrationStatus(dataMigrationDetails.getPsp()));
        details.put("CANALI_NODO", getTableMigrationStatus(dataMigrationDetails.getCanaliNodo()));
        details.put("CANALI", getTableMigrationStatus(dataMigrationDetails.getCanali()));
        details.put("TIPI_VERSAMENTO", getTableMigrationStatus(dataMigrationDetails.getTipiVersamento()));
        details.put("CANALE_TIPO_VERSAMENTO", getTableMigrationStatus(dataMigrationDetails.getCanaleTipoVersamento()));
        details.put("PSP_CANALE_TIPO_VERSAMENTO", getTableMigrationStatus(dataMigrationDetails.getPspCanaleTipoVersamento()));
        details.put("DIZIONARIO_METADATI", getTableMigrationStatus(dataMigrationDetails.getDizionarioMetadati()));
        details.put("CDI_MASTER", getTableMigrationStatus(dataMigrationDetails.getCdiMaster()));
        details.put("CDI_DETAIL", getTableMigrationStatus(dataMigrationDetails.getCdiDetail()));
        details.put("CDI_FASCIA_COSTO_SERVIZIO", getTableMigrationStatus(dataMigrationDetails.getCdiFasciaCostoServizio()));
        details.put("CDI_INFORMAZIONI_SERVIZIO", getTableMigrationStatus(dataMigrationDetails.getCdiInformazioniServizio()));
        details.put("CDI_PREFERENCES", getTableMigrationStatus(dataMigrationDetails.getCdiPreferences()));
        details.put("ELENCO_SERVIZI", getTableMigrationStatus(dataMigrationDetails.getElencoServizi()));
        details.put("CDS_CATEGORIE", getTableMigrationStatus(dataMigrationDetails.getCdsCategorie()));
        details.put("CDS_SOGGETTO", getTableMigrationStatus(dataMigrationDetails.getCdsSoggetto()));
        details.put("CDS_SERVIZIO", getTableMigrationStatus(dataMigrationDetails.getCdsServizio()));
        details.put("CDS_SOGGETTO_SERVIZIO", getTableMigrationStatus(dataMigrationDetails.getCdsSoggettoServizio()));
        details.put("CONFIGURATION_KEYS", getTableMigrationStatus(dataMigrationDetails.getConfigurationKeys()));
        details.put("WFESP_PLUGIN_CONF", getTableMigrationStatus(dataMigrationDetails.getWfespPluginConf()));
        details.put("FTP_SERVERS", getTableMigrationStatus(dataMigrationDetails.getFtpServers()));
        details.put("PDD", getTableMigrationStatus(dataMigrationDetails.getPdd()));
        details.put("GDE_CONFIG", getTableMigrationStatus(dataMigrationDetails.getGdeConfig()));
        details.put("QUADRATURE_SCHED", getTableMigrationStatus(dataMigrationDetails.getQuadratureSched()));

        Timestamp endTime = new Timestamp(now.getTime() + 7L);
        dataMigration.setEnd(endTime);

        MigrationStatus expectedMigrationStatus = MigrationStatus.builder()
                .migrationStart(String.valueOf(now))
                .migrationLastRestart(null)
                .elapsedTime(7L)
                .status(null)
                .details(details)
                .build();

        MigrationStatus migrationStatus = migrationService.convert(dataMigration);

        assertEquals(expectedMigrationStatus, migrationStatus);
    }

    @Test
    public void testAsyncStart() throws Exception {
        Method asyncStartMethod = MigrationService.class.getDeclaredMethod("asyncStart", StepName.class);
        ReflectionUtils.makeAccessible(asyncStartMethod);

        asyncStartMethod.invoke(migrationService, StepName.EXECUTE_BINARY_FILE_TABLE_MIGRATION);
        verify(fsmExecutor).start(StepName.EXECUTE_BINARY_FILE_TABLE_MIGRATION);
    }

    private TableMigrationStatus getTableMigrationStatus(DataMigrationStatus dataMigrationStatus) {
        return TableMigrationStatus.builder()
                .status(dataMigrationStatus.getStatus())
                .start(dataMigrationStatus.getStart() != null  ? dataMigrationStatus.getStart().toString() : null)
                .elapsedTime(dataMigrationStatus.getEnd() == null ?
                        0L : CommonUtils.getElapsedTime(dataMigrationStatus.getStart(), dataMigrationStatus.getEnd()))
                .records(dataMigrationStatus.getRecords())
                .build();
    }
}
