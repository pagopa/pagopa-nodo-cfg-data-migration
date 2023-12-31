package it.gov.pagopa.nodo.datamigration.service;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.fsm.FSMExecutor;
import it.gov.pagopa.nodo.datamigration.model.migration.MigrationStatus;
import it.gov.pagopa.nodo.datamigration.model.migration.TableMigrationStatus;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MigrationService {

    @Autowired
    @Qualifier("executor")
    private FSMExecutor fsmExecutor;

    @Async
    public void startMigration() {
        fsmExecutor.start();
    }

    public void reStartMigration() {
        StepName lastExecutedName = fsmExecutor.restart();
        asyncStart(lastExecutedName);
    }

    public void forcedStopMigration() {
        fsmExecutor.forceStop();
    }

    public MigrationStatus getMigrationStatus() {
        return convert(fsmExecutor.getLastMigrationStatus());
    }

    @Async
    protected void asyncStart(StepName lastExecutedName) {
        fsmExecutor.start(lastExecutedName);
    }

    public MigrationStatus convert(DataMigration dataMigration) {
        // set tables status
        DataMigrationDetails migrationDetails = dataMigration.getDetails();
        Map<String, TableMigrationStatus> details = new HashMap<>();
        details.put("INTERMEDIARI_PA", getTableMigrationStatus(migrationDetails.getIntermediariPa()));
        details.put("PA", getTableMigrationStatus(migrationDetails.getPa()));
        details.put("STAZIONI", getTableMigrationStatus(migrationDetails.getStazioni()));
        details.put("PA_STAZIONE_PA", getTableMigrationStatus(migrationDetails.getPaStazioniPa()));
        details.put("CODIFICHE", getTableMigrationStatus(migrationDetails.getCodifiche()));
        details.put("CODIFICHE_PA", getTableMigrationStatus(migrationDetails.getCodifichePa()));
        details.put("BINARY_FILE", getTableMigrationStatus(migrationDetails.getBinaryFile()));
        details.put("INFORMATIVE_CONTO_ACCREDITO_MASTER", getTableMigrationStatus(migrationDetails.getInformativeContoAccreditoMaster()));
        details.put("INFORMATIVE_CONTO_ACCREDITO_DETAIL", getTableMigrationStatus(migrationDetails.getInformativeContoAccreditoDetail()));
        details.put("INFORMATIVE_PA_MASTER", getTableMigrationStatus(migrationDetails.getInformativePaMaster()));
        details.put("INFORMATIVE_PA_DETAIL", getTableMigrationStatus(migrationDetails.getInformativePaDetail()));
        details.put("INFORMATIVE_PA_FASCE", getTableMigrationStatus(migrationDetails.getInformativePaFasce()));
        details.put("INTERMEDIARI_PSP", getTableMigrationStatus(migrationDetails.getIntermediariPsp()));
        details.put("PSP", getTableMigrationStatus(migrationDetails.getPsp()));
        details.put("CANALI_NODO", getTableMigrationStatus(migrationDetails.getCanaliNodo()));
        details.put("CANALI", getTableMigrationStatus(migrationDetails.getCanali()));
        details.put("TIPI_VERSAMENTO", getTableMigrationStatus(migrationDetails.getTipiVersamento()));
        details.put("CANALE_TIPO_VERSAMENTO", getTableMigrationStatus(migrationDetails.getCanaleTipoVersamento()));
        details.put("PSP_CANALE_TIPO_VERSAMENTO", getTableMigrationStatus(migrationDetails.getPspCanaleTipoVersamento()));
        details.put("DIZIONARIO_METADATI", getTableMigrationStatus(migrationDetails.getDizionarioMetadati()));
        details.put("CDI_MASTER", getTableMigrationStatus(migrationDetails.getCdiMaster()));
        details.put("CDI_DETAIL", getTableMigrationStatus(migrationDetails.getCdiDetail()));
        details.put("CDI_FASCIA_COSTO_SERVIZIO", getTableMigrationStatus(migrationDetails.getCdiFasciaCostoServizio()));
        details.put("CDI_INFORMAZIONI_SERVIZIO", getTableMigrationStatus(migrationDetails.getCdiInformazioniServizio()));
        details.put("CDI_PREFERENCES", getTableMigrationStatus(migrationDetails.getCdiPreferences()));
        details.put("ELENCO_SERVIZI", getTableMigrationStatus(migrationDetails.getElencoServizi()));
        details.put("CDS_CATEGORIE", getTableMigrationStatus(migrationDetails.getCdsCategorie()));
        details.put("CDS_SOGGETTO", getTableMigrationStatus(migrationDetails.getCdsSoggetto()));
        details.put("CDS_SERVIZIO", getTableMigrationStatus(migrationDetails.getCdsServizio()));
        details.put("CDS_SOGGETTO_SERVIZIO", getTableMigrationStatus(migrationDetails.getCdsSoggettoServizio()));
        details.put("CONFIGURATION_KEYS", getTableMigrationStatus(migrationDetails.getConfigurationKeys()));
        details.put("WFESP_PLUGIN_CONF", getTableMigrationStatus(migrationDetails.getWfespPluginConf()));
        details.put("FTP_SERVERS", getTableMigrationStatus(migrationDetails.getFtpServers()));
        details.put("PDD", getTableMigrationStatus(migrationDetails.getPdd()));
        details.put("GDE_CONFIG", getTableMigrationStatus(migrationDetails.getGdeConfig()));
        details.put("QUADRATURE_SCHED", getTableMigrationStatus(migrationDetails.getQuadratureSched()));
        details.put("IBAN", getTableMigrationStatus(migrationDetails.getIban()));
        details.put("IBAN_ATTRIBUTES", getTableMigrationStatus(migrationDetails.getIbanAttributes()));
        details.put("IBAN_ATTRIBUTES_MASTER", getTableMigrationStatus(migrationDetails.getIbanAttributesMaster()));
        details.put("IBAN_MASTER", getTableMigrationStatus(migrationDetails.getIbanMaster()));
        details.put("ICA_BINARY_FILE", getTableMigrationStatus(migrationDetails.getIcaBinaryFile()));

        // complete migration status
        return MigrationStatus.builder()
                .migrationStart(dataMigration.getStart().toString())
                .migrationLastRestart(dataMigration.getRestart() != null ? dataMigration.getRestart().toString() : null)
                .elapsedTime(dataMigration.getEnd() != null ?
                        CommonUtils.getElapsedTime(dataMigration.getStart(), dataMigration.getEnd()) :
                        CommonUtils.getElapsedTime(dataMigration.getStart(), CommonUtils.now()))
                .status(dataMigration.getStatus())
                .details(details)
                .build();
    }

    private TableMigrationStatus getTableMigrationStatus(DataMigrationStatus dataMigrationStatus) {
        return TableMigrationStatus.builder()
                .status(dataMigrationStatus.getStatus())
                .start(dataMigrationStatus.getStart() != null  ? dataMigrationStatus.getStart().toString() : null)
                .elapsedTime(dataMigrationStatus.getEnd() == null ? 0L : CommonUtils.getElapsedTime(dataMigrationStatus.getStart(), dataMigrationStatus.getEnd()))
                .records(dataMigrationStatus.getRecords())
                .build();
    }
}
