package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStatusSavingException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationTruncateAllTablesException;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.repository.postgres.*;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("START")
public class StartStep extends Step {

    @Autowired private BinaryFileDestRepository binaryFileRepo;
    @Autowired private CanaleTipoVersamentoDestRepository canaleTipoVersamentoRepo;
    @Autowired private CanaliDestRepository canaliRepo;
    @Autowired private CanaliNodoDestRepository canaliNodoRepo;
    @Autowired private CdiDetailDestRepository cdiDetailRepo;
    @Autowired private CdiFasciaCostoServizioDestRepository cdiFasciaCostoServizioRepo;
    @Autowired private CdiInformazioniServizioDestRepository cdiInformazioniServizioRepo;
    @Autowired private CdiMasterDestRepository cdiMasterRepo;
    @Autowired private CdiPreferenceDestRepository cdiPreferenceRepo;
    @Autowired private CdsCategoriaDestRepository cdsCategoriaRepo;
    @Autowired private CdsServizioDestRepository cdsServizioRepo;
    @Autowired private CdsSoggettoDestRepository cdsSoggettoRepo;
    @Autowired private CdsSoggettoServizioDestRepository cdsSoggettoServizioRepo;
    @Autowired private CodificheDestRepository codificheRepo;
    @Autowired private CodifichePaDestRepository codifichePaRepo;
    @Autowired private ConfigurationKeysDestRepository configurationKeysRepo;
    @Autowired private DizionarioMetadatiDestRepository dizionarioMetadatiRepo;
    @Autowired private ElencoServiziDestRepository elencoServiziRepo;
    @Autowired private FtpServersDestRepository ftpServersRepo;
    @Autowired private GdeConfigDestRepository gdeConfigRepo;
    @Autowired private InformativeContoAccreditoDetailDestRepository informativeContoAccreditoDetailRepo;
    @Autowired private InformativeContoAccreditoMasterDestRepository informativeContoAccreditoMasterRepo;
    @Autowired private InformativePaDetailDestRepository informativePaDetailRepo;
    @Autowired private InformativePaFasceDestRepository informativePaFasceRepo;
    @Autowired private InformativePaMasterDestRepository informativePaMasterRepo;
    @Autowired private IntermediariPaDestRepository intermediariPaRepo;
    @Autowired private IntermediariPspDestRepository intermediariPspRepo;
    @Autowired private PaDestRepository paRepo;
    @Autowired private PaStazionePaDestRepository paStazionePaRepo;
    @Autowired private PddDestRepository pddRepo;
    @Autowired private PspCanaleTipoVersamentoDestRepository pspCanaleTipoVersamentoRepo;
    @Autowired private PspDestRepository pspRepo;
    @Autowired private QuadratureSchedDestRepository quadratureSchedRepo;
    @Autowired private StazioniDestRepository stazioniRepo;
    @Autowired private TipiVersamentoDestRepository tipiVersamentoRepo;
    @Autowired private WfespPluginConfDestRepository wfespPluginConfRepo;

    @Override
    public void executeStep() throws MigrationStepException {
        // resetting flags and creating a new record in the CFG_DATA_MIGRATION table
        activateMigration();
        // deleting all data in all tables
        truncateAllTables();
    }

    @Override
    public StepName getNextState() {
        return StepName.EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION;
    }

    @Override
    public String getStepName() {
        return StepName.START.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return null;
    }

    private void activateMigration() throws MigrationStatusSavingException {
        // resetting migration flags
        this.sharedState.resetStates();
        this.sharedState.lock();
        // save migration status
        try {
            String id = UUID.randomUUID().toString();
            this.sharedState.setDataMigrationStateId(id);
            DataMigration dataMigration = DataMigration.builder()
                    .id(id)
                    .start(CommonUtils.now())
                    .status(MigrationStepStatus.IN_PROGRESS.toString())
                    .lastExecutedStep(StepName.START.toString())
                    .details(DataMigrationDetails.builder()
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
                            .build())
                    .build();
            cfgDataMigrationRepo.save(dataMigration);
        } catch (DataAccessException e) {
            throw new MigrationStatusSavingException(e);
        }
    }

    private void truncateAllTables() throws MigrationTruncateAllTablesException {
        try {
            log.info(" - Starting deleting all data from GDE_CONFIG...");
            deleteAndFlush(gdeConfigRepo);
            log.info(" - Deleted all data from GDE_CONFIG. Starting deleting all data from PDD...");
            deleteAndFlush(pddRepo);
            log.info(" - Deleted all data from PDD. Starting deleting all data from FTP_SERVER...");
            deleteAndFlush(ftpServersRepo);
            log.info(" - Deleted all data from FTP_SERVER. Starting deleting all data from CONFIGURATION_KEYS...");
            deleteAndFlush(configurationKeysRepo);
            log.info(" - Deleted all data from CONFIGURATION_KEYS. Starting deleting all data from CDS_SOGGETTO_SERVIZIO...");
            deleteAndFlush(cdsSoggettoServizioRepo);
            log.info(" - Deleted all data from CDS_SOGGETTO_SERVIZIO. Starting deleting all data from CDS_SERVIZIO...");
            deleteAndFlush(cdsServizioRepo);
            log.info(" - Deleted all data from CDS_SERVIZIO. Starting deleting all data from CDS_SOGGETTO...");
            deleteAndFlush(cdsSoggettoRepo);
            log.info(" - Deleted all data from CDS_SOGGETTO. Starting deleting all data from CDS_CATEGORIA...");
            deleteAndFlush(cdsCategoriaRepo);
            log.info(" - Deleted all data from CDS_CATEGORIA. Starting deleting all data from ELENCO_SERVIZI...");
            deleteAndFlush(elencoServiziRepo);
            log.info(" - Deleted all data from ELENCO_SERVIZI. Starting deleting all data from CDI_PREFERENCES...");
            deleteAndFlush(cdiPreferenceRepo);
            log.info(" - Deleted all data from CDI_PREFERENCES. Starting deleting all data from CDI_INFORMAZIONI_SERVIZIO...");
            deleteAndFlush(cdiInformazioniServizioRepo);
            log.info(" - Deleted all data from CDI_INFORMAZIONI_SERVIZIO. Starting deleting all data from CDI_FASCIA_COSTO_SERVIZIO...");
            deleteAndFlush(cdiFasciaCostoServizioRepo);
            log.info(" - Deleted all data from CDI_FASCIA_COSTO_SERVIZIO. Starting deleting all data from CDI_DETAIL...");
            deleteAndFlush(cdiDetailRepo);
            log.info(" - Deleted all data from CDI_DETAIL. Starting deleting all data from CDI_MASTER...");
            deleteAndFlush(cdiMasterRepo);
            log.info(" - Deleted all data from CDI_MASTER. Starting deleting all data from DIZIONARIO_METADATI...");
            deleteAndFlush(dizionarioMetadatiRepo);
            log.info(" - Deleted all data from DIZIONARIO_METADATI. Starting deleting all data from PSP_CANALE_TIPO_VERSAMENTO...");
            deleteAndFlush(pspCanaleTipoVersamentoRepo);
            log.info(" - Deleted all data from PSP_CANALE_TIPO_VERSAMENTO. Starting deleting all data from CANALE_TIPO_VERSAMENTO...");
            deleteAndFlush(canaleTipoVersamentoRepo);
            log.info(" - Deleted all data from CANALE_TIPO_VERSAMENTO. Starting deleting all data from TIPI_VERSAMENTO...");
            deleteAndFlush(tipiVersamentoRepo);
            log.info(" - Deleted all data from TIPI_VERSAMENTO. Starting deleting all data from CANALI_REPO...");
            deleteAndFlush(canaliRepo);
            log.info(" - Deleted all data from CANALI_REPO. Starting deleting all data from CANALI_NODO...");
            deleteAndFlush(canaliNodoRepo);
            log.info(" - Deleted all data from CANALI_NODO. Starting deleting all data from WFESP_PLUGIN_CONF...");
            deleteAndFlush(wfespPluginConfRepo);
            log.info(" - Deleted all data from WFESP_PLUGIN_CONF. Starting deleting all data from PSP...");
            deleteAndFlush(pspRepo);
            log.info(" - Deleted all data from PSP. Starting deleting all data from INTERMEDIARI_PSP...");
            deleteAndFlush(intermediariPspRepo);
            log.info(" - Deleted all data from INTERMEDIARI_PSP. Starting deleting all data from INFORMATIVE_PA_FASCE...");
            deleteAndFlush(informativePaFasceRepo);
            log.info(" - Deleted all data from INFORMATIVE_PA_FASCE. Starting deleting all data from INFORMATIVE_PA_DETAIL...");
            deleteAndFlush(informativePaDetailRepo);
            log.info(" - Deleted all data from INFORMATIVE_PA_DETAIL. Starting deleting all data from INFORMATIVE_PA_MASTER...");
            deleteAndFlush(informativePaMasterRepo);
            log.info(" - Deleted all data from INFORMATIVE_PA_MASTER. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL...");
            deleteAndFlush(informativeContoAccreditoDetailRepo);
            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_MASTER...");
            deleteAndFlush(informativeContoAccreditoMasterRepo);
            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_MASTER. Starting deleting all data from BINARY_FILE...");
            deleteAndFlush(binaryFileRepo);
            log.info(" - Deleted all data from BINARY_FILE. Starting deleting all data from CODIFICHE_PA...");
            deleteAndFlush(codifichePaRepo);
            log.info(" - Deleted all data from CODIFICHE_PA. Starting deleting all data from CODIFICHE...");
            deleteAndFlush(codificheRepo);
            log.info(" - Deleted all data from CODIFICHE. Starting deleting all data from PA_STAZIONE_PA...");
            deleteAndFlush(paStazionePaRepo);
            log.info(" - Deleted all data from PA_STAZIONE_PA. Starting deleting all data from STAZIONI...");
            deleteAndFlush(stazioniRepo);
            log.info(" - Deleted all data from STAZIONI. Starting deleting all data from PA...");
            deleteAndFlush(paRepo);
            log.info(" - Deleted all data from PA. Starting deleting all data from INTERMEDIARI_PA...");
            deleteAndFlush(intermediariPaRepo);
            log.info(" - Deleted all data from INTERMEDIARI_PA. Starting deleting all data from QUADRATURE_SCHED...");
            deleteAndFlush(quadratureSchedRepo);
            log.info(" - Deleted all data from QUADRATURE_SCHED. Ended deleting all previous data!.");
        } catch (DataAccessException e) {
            throw new MigrationTruncateAllTablesException(e);
        }
    }

    private void deleteAndFlush(JpaRepository repo) {
        repo.deleteAll();
        repo.flush();
    }
}
