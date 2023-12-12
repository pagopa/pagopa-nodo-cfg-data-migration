package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.DatabaseConnectionException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStatusSavingException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationTruncateAllTablesException;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.service.HealthCheckService;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("START")
public class StartStep extends Step {

    @PersistenceUnit(unitName="postgresqlUnit")
    private EntityManagerFactory emFactory;

    @Value("${persistence.postgresql.default_schema}")
    private String schema;

    @Autowired private HealthCheckService healthCheckService;

    @Override
    public void executeStep() throws MigrationStepException {
        // execute an health check and find if a DB is inaccessible
        if (!this.healthCheckService.getHealthCheckForOracleDB()) {
            throw new DatabaseConnectionException("OracleDB");
        }
        if (!this.healthCheckService.getHealthCheckForPostgresDB()) {
            throw new DatabaseConnectionException("PostgreSQL");
        }
        // resetting flags and creating a new record in the CFG_DATA_MIGRATION table
        activateMigration();
        // deleting all data in all tables
        truncateAllTables();
    }

    @Override
    public StepName getNextState() {
//        return StepName.EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION;
        return StepName.EXECUTE_ELENCO_SERVIZI_TABLE_MIGRATION;
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
                            .iban(new DataMigrationStatus())
                            .ibanAttributes(new DataMigrationStatus())
                            .ibanMaster(new DataMigrationStatus())
                            .ibanAttributesMaster(new DataMigrationStatus())
                            .icaBinaryFile(new DataMigrationStatus())
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
            EntityManager em = emFactory.createEntityManager();
//            log.info(" - Starting deleting all data from GDE_CONFIG...");
//            deleteAndFlush(em, "GDE_CONFIG");
//            log.info(" - Deleted all data from GDE_CONFIG. Starting deleting all data from PDD...");
//            deleteAndFlush(em, "PDD");
//            log.info(" - Deleted all data from PDD. Starting deleting all data from FTP_SERVERS...");
//            deleteAndFlush(em, "FTP_SERVERS");
//            log.info(" - Deleted all data from FTP_SERVER. Starting deleting all data from CONFIGURATION_KEYS...");
//            deleteAndFlush(em, "CONFIGURATION_KEYS");
//            log.info(" - Deleted all data from CONFIGURATION_KEYS. Starting deleting all data from CDS_SOGGETTO_SERVIZIO...");
//            deleteAndFlush(em, "CDS_SOGGETTO_SERVIZIO");
//            log.info(" - Deleted all data from CDS_SOGGETTO_SERVIZIO. Starting deleting all data from CDS_SERVIZIO...");
//            deleteAndFlush(em, "CDS_SERVIZIO");
//            log.info(" - Deleted all data from CDS_SERVIZIO. Starting deleting all data from CDS_SOGGETTO...");
//            deleteAndFlush(em, "CDS_SOGGETTO");
//            log.info(" - Deleted all data from CDS_SOGGETTO. Starting deleting all data from CDS_CATEGORIA...");
//            deleteAndFlush(em, "CDS_CATEGORIE");
            log.info(" - Deleted all data from CDS_CATEGORIE. Starting deleting all data from ELENCO_SERVIZI...");
            deleteAndFlush(em, "ELENCO_SERVIZI");
            log.info(" - Deleted all data from ELENCO_SERVIZI. Starting deleting all data from CDI_PREFERENCES...");
//            deleteAndFlush(em, "CDI_PREFERENCES");
//            log.info(" - Deleted all data from CDI_PREFERENCES. Starting deleting all data from CDI_INFORMAZIONI_SERVIZIO...");
//            deleteAndFlush(em, "CDI_INFORMAZIONI_SERVIZIO");
//            log.info(" - Deleted all data from CDI_INFORMAZIONI_SERVIZIO. Starting deleting all data from CDI_FASCIA_COSTO_SERVIZIO...");
//            deleteAndFlush(em, "CDI_FASCIA_COSTO_SERVIZIO");
//            log.info(" - Deleted all data from CDI_FASCIA_COSTO_SERVIZIO. Starting deleting all data from CDI_DETAIL...");
//            deleteAndFlush(em, "CDI_DETAIL");
//            log.info(" - Deleted all data from CDI_DETAIL. Starting deleting all data from CDI_MASTER...");
//            deleteAndFlush(em, "CDI_MASTER");
//            log.info(" - Deleted all data from CDI_MASTER. Starting deleting all data from DIZIONARIO_METADATI...");
//            deleteAndFlush(em, "DIZIONARIO_METADATI");
//            log.info(" - Deleted all data from DIZIONARIO_METADATI. Starting deleting all data from PSP_CANALE_TIPO_VERSAMENTO...");
//            deleteAndFlush(em, "PSP_CANALE_TIPO_VERSAMENTO");
//            log.info(" - Deleted all data from PSP_CANALE_TIPO_VERSAMENTO. Starting deleting all data from CANALE_TIPO_VERSAMENTO...");
//            deleteAndFlush(em, "CANALE_TIPO_VERSAMENTO");
//            log.info(" - Deleted all data from CANALE_TIPO_VERSAMENTO. Starting deleting all data from TIPI_VERSAMENTO...");
//            deleteAndFlush(em, "TIPI_VERSAMENTO");
//            log.info(" - Deleted all data from TIPI_VERSAMENTO. Starting deleting all data from CANALI_REPO...");
//            deleteAndFlush(em, "CANALI");
//            log.info(" - Deleted all data from CANALI_REPO. Starting deleting all data from CANALI_NODO...");
//            deleteAndFlush(em, "CANALI_NODO");
//            log.info(" - Deleted all data from CANALI_NODO. Starting deleting all data from WFESP_PLUGIN_CONF...");
//            deleteAndFlush(em, "WFESP_PLUGIN_CONF");
//            log.info(" - Deleted all data from WFESP_PLUGIN_CONF. Starting deleting all data from PSP...");
//            deleteAndFlush(em, "PSP");
//            log.info(" - Deleted all data from PSP. Starting deleting all data from INTERMEDIARI_PSP...");
//            deleteAndFlush(em, "INTERMEDIARI_PSP");
//            log.info(" - Deleted all data from INTERMEDIARI_PSP. Starting deleting all data from INFORMATIVE_PA_FASCE...");
//            deleteAndFlush(em, "INFORMATIVE_PA_FASCE");
//            log.info(" - Deleted all data from INFORMATIVE_PA_FASCE. Starting deleting all data from INFORMATIVE_PA_DETAIL...");
//            deleteAndFlush(em, "INFORMATIVE_PA_DETAIL");
//            log.info(" - Deleted all data from INFORMATIVE_PA_DETAIL. Starting deleting all data from INFORMATIVE_PA_MASTER...");
//            deleteAndFlush(em, "INFORMATIVE_PA_MASTER");
//            log.info(" - Deleted all data from INFORMATIVE_PA_MASTER. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL...");
//            deleteAndFlush(em, "INFORMATIVE_CONTO_ACCREDITO_DETAIL");
//            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_MASTER...");
//            deleteAndFlush(em, "INFORMATIVE_CONTO_ACCREDITO_MASTER");
//            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_MASTER. Starting deleting all data from IBAN_ATTRIBUTES_MASTER...");
//            deleteAndFlush(em, "IBAN_ATTRIBUTES_MASTER");
//            log.info(" - Deleted all data from IBAN_ATTRIBUTES_MASTER. Starting deleting all data from IBAN_MASTER...");
//            deleteAndFlush(em, "IBAN_MASTER");
//            log.info(" - Deleted all data from IBAN_MASTER. Starting deleting all data from IBAN_ATTRIBUTES...");
//            deleteAndFlush(em, "IBAN_ATTRIBUTES");
//            log.info(" - Deleted all data from IBAN_ATTRIBUTES. Starting deleting all data from IBAN...");
//            deleteAndFlush(em, "IBAN");
//            log.info(" - Deleted all data from IBAN. Starting deleting all data from ICA_BINARY_FILE...");
//            deleteAndFlush(em, "ICA_BINARY_FILE");
//            log.info(" - Deleted all data from ICA_BINARY_FILE. Starting deleting all data from BINARY_FILE...");
//            deleteAndFlush(em, "BINARY_FILE");
//            log.info(" - Deleted all data from BINARY_FILE.");
//            log.info(" - Starting deleting all data from CODIFICHE_PA...");
//            deleteAndFlush(em, "CODIFICHE_PA");
//            log.info(" - Deleted all data from CODIFICHE_PA. Starting deleting all data from CODIFICHE...");
//            deleteAndFlush(em, "CODIFICHE");
//            log.info(" - Deleted all data from CODIFICHE. Starting deleting all data from PA_STAZIONE_PA...");
//            deleteAndFlush(em, "PA_STAZIONE_PA");
//            log.info(" - Deleted all data from PA_STAZIONE_PA. Starting deleting all data from STAZIONI...");
//            deleteAndFlush(em, "STAZIONI");
//            log.info(" - Deleted all data from STAZIONI. Starting deleting all data from PA...");
//            deleteAndFlush(em, "PA");
//            log.info(" - Deleted all data from PA. Starting deleting all data from INTERMEDIARI_PA...");
//            deleteAndFlush(em, "INTERMEDIARI_PA");
//            log.info(" - Deleted all data from INTERMEDIARI_PA. Starting deleting all data from QUADRATURE_SCHED...");
//            deleteAndFlush(em, "QUADRATURE_SCHED");
//            log.info(" - Deleted all data from QUADRATURE_SCHED. Ended deleting all previous data!.");
            log.info(" - Ended deleting all previous data!.");
        } catch (DataAccessException e) {
            throw new MigrationTruncateAllTablesException(e);
        }
    }

    @Transactional
    private void deleteAndFlush(EntityManager destEM, String table) {
        destEM.getTransaction().begin();
        destEM.createNativeQuery(String.format("DELETE FROM %s.%s", schema, table))
                .executeUpdate();
        destEM.flush();
        destEM.clear();
        destEM.getTransaction().commit();
    }
}
