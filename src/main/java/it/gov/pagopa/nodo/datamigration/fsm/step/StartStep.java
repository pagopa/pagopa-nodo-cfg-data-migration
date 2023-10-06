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
import it.gov.pagopa.nodo.datamigration.repository.oracle.OracleDBSystemRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.*;
import it.gov.pagopa.nodo.datamigration.service.HealthCheckService;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("START")
public class StartStep extends Step {

    @PersistenceContext(unitName="postgresqlUnit")
    private EntityManager destEM;

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
            deleteAndFlush("GDE_CONFIG");
            log.info(" - Deleted all data from GDE_CONFIG. Starting deleting all data from PDD...");
            deleteAndFlush("PDD");
            log.info(" - Deleted all data from PDD. Starting deleting all data from FTP_SERVER...");
            deleteAndFlush("FTP_SERVER");
            log.info(" - Deleted all data from FTP_SERVER. Starting deleting all data from CONFIGURATION_KEYS...");
            deleteAndFlush("CONFIGURATION_KEYS");
            log.info(" - Deleted all data from CONFIGURATION_KEYS. Starting deleting all data from CDS_SOGGETTO_SERVIZIO...");
            deleteAndFlush("CDS_SOGGETTO_SERVIZIO");
            log.info(" - Deleted all data from CDS_SOGGETTO_SERVIZIO. Starting deleting all data from CDS_SERVIZIO...");
            deleteAndFlush("CDS_SERVIZIO");
            log.info(" - Deleted all data from CDS_SERVIZIO. Starting deleting all data from CDS_SOGGETTO...");
            deleteAndFlush("CDS_SOGGETTO");
            log.info(" - Deleted all data from CDS_SOGGETTO. Starting deleting all data from CDS_CATEGORIA...");
            deleteAndFlush("CDS_CATEGORIA");
            log.info(" - Deleted all data from CDS_CATEGORIA. Starting deleting all data from ELENCO_SERVIZI...");
            deleteAndFlush("ELENCO_SERVIZI");
            log.info(" - Deleted all data from ELENCO_SERVIZI. Starting deleting all data from CDI_PREFERENCES...");
            deleteAndFlush("CDI_PREFERENCES");
            log.info(" - Deleted all data from CDI_PREFERENCES. Starting deleting all data from CDI_INFORMAZIONI_SERVIZIO...");
            deleteAndFlush("CDI_INFORMAZIONI_SERVIZIO");
            log.info(" - Deleted all data from CDI_INFORMAZIONI_SERVIZIO. Starting deleting all data from CDI_FASCIA_COSTO_SERVIZIO...");
            deleteAndFlush("CDI_FASCIA_COSTO_SERVIZIO");
            log.info(" - Deleted all data from CDI_FASCIA_COSTO_SERVIZIO. Starting deleting all data from CDI_DETAIL...");
            deleteAndFlush("CDI_DETAIL");
            log.info(" - Deleted all data from CDI_DETAIL. Starting deleting all data from CDI_MASTER...");
            deleteAndFlush("CDI_MASTER");
            log.info(" - Deleted all data from CDI_MASTER. Starting deleting all data from DIZIONARIO_METADATI...");
            deleteAndFlush("DIZIONARIO_METADATI");
            log.info(" - Deleted all data from DIZIONARIO_METADATI. Starting deleting all data from PSP_CANALE_TIPO_VERSAMENTO...");
            deleteAndFlush("PSP_CANALE_TIPO_VERSAMENTO");
            log.info(" - Deleted all data from PSP_CANALE_TIPO_VERSAMENTO. Starting deleting all data from CANALE_TIPO_VERSAMENTO...");
            deleteAndFlush("CANALE_TIPO_VERSAMENTO");
            log.info(" - Deleted all data from CANALE_TIPO_VERSAMENTO. Starting deleting all data from TIPI_VERSAMENTO...");
            deleteAndFlush("TIPI_VERSAMENTO");
            log.info(" - Deleted all data from TIPI_VERSAMENTO. Starting deleting all data from CANALI_REPO...");
            deleteAndFlush("CANALI_REPO");
            log.info(" - Deleted all data from CANALI_REPO. Starting deleting all data from CANALI_NODO...");
            deleteAndFlush("CANALI_NODO");
            log.info(" - Deleted all data from CANALI_NODO. Starting deleting all data from WFESP_PLUGIN_CONF...");
            deleteAndFlush("WFESP_PLUGIN_CONF");
            log.info(" - Deleted all data from WFESP_PLUGIN_CONF. Starting deleting all data from PSP...");
            deleteAndFlush("PSP");
            log.info(" - Deleted all data from PSP. Starting deleting all data from INTERMEDIARI_PSP...");
            deleteAndFlush("INTERMEDIARI_PSP");
            log.info(" - Deleted all data from INTERMEDIARI_PSP. Starting deleting all data from INFORMATIVE_PA_FASCE...");
            deleteAndFlush("INFORMATIVE_PA_FASCE");
            log.info(" - Deleted all data from INFORMATIVE_PA_FASCE. Starting deleting all data from INFORMATIVE_PA_DETAIL...");
            deleteAndFlush("INFORMATIVE_PA_DETAIL");
            log.info(" - Deleted all data from INFORMATIVE_PA_DETAIL. Starting deleting all data from INFORMATIVE_PA_MASTER...");
            deleteAndFlush("INFORMATIVE_PA_MASTER");
            log.info(" - Deleted all data from INFORMATIVE_PA_MASTER. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL...");
            deleteAndFlush("INFORMATIVE_CONTO_ACCREDITO_DETAIL");
            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_DETAIL. Starting deleting all data from INFORMATIVE_CONTO_ACCREDITO_MASTER...");
            deleteAndFlush("INFORMATIVE_CONTO_ACCREDITO_MASTER");
            log.info(" - Deleted all data from INFORMATIVE_CONTO_ACCREDITO_MASTER. Starting deleting all data from BINARY_FILE...");
            deleteAndFlush("BINARY_FILE");
            log.info(" - Deleted all data from BINARY_FILE. Starting deleting all data from CODIFICHE_PA...");
            deleteAndFlush("CODIFICHE_PA");
            log.info(" - Deleted all data from CODIFICHE_PA. Starting deleting all data from CODIFICHE...");
            deleteAndFlush("CODIFICHE");
            log.info(" - Deleted all data from CODIFICHE. Starting deleting all data from PA_STAZIONE_PA...");
            deleteAndFlush("PA_STAZIONE_PA");
            log.info(" - Deleted all data from PA_STAZIONE_PA. Starting deleting all data from STAZIONI...");
            deleteAndFlush("STAZIONI");
            log.info(" - Deleted all data from STAZIONI. Starting deleting all data from PA...");
            deleteAndFlush("PA");
            log.info(" - Deleted all data from PA. Starting deleting all data from INTERMEDIARI_PA...");
            deleteAndFlush("INTERMEDIARI_PA");
            log.info(" - Deleted all data from INTERMEDIARI_PA. Starting deleting all data from QUADRATURE_SCHED...");
            deleteAndFlush("QUADRATURE_SCHED");
            log.info(" - Deleted all data from QUADRATURE_SCHED. Ended deleting all previous data!.");
        } catch (DataAccessException e) {
            throw new MigrationTruncateAllTablesException(e);
        }
    }

    private void deleteAndFlush(String table) {
        EntityTransaction transaction = destEM.getTransaction();
        transaction.begin();
        destEM.createNativeQuery(String.format("DELETE FROM %s.%s", schema, table))
                .executeUpdate();
        destEM.flush();
        destEM.clear();
        transaction.commit();
    }
}
