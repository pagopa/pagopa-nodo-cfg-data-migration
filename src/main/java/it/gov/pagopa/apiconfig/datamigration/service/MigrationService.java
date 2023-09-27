package it.gov.pagopa.apiconfig.datamigration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.exception.AppError;
import it.gov.pagopa.apiconfig.datamigration.exception.AppException;
import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import it.gov.pagopa.apiconfig.datamigration.model.migration.MigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.model.migration.TableMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import org.modelmapper.spi.MappingContext;
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

    @Autowired
    private CfgDataMigrationRepository cfgDataMigrationRepo;

    @Autowired
    private ObjectMapper mapper;

    @Async
    public void startMigration() throws Exception {
        fsmExecutor.start();
    }

    @Async
    public void reStartMigration() throws Exception {
        fsmExecutor.restart();
    }

    public void forcedStopMigration() {
        fsmExecutor.forceStop();
    }

    public MigrationStatus getMigrationStatus() {
        DataMigration dataMigrationStatus = cfgDataMigrationRepo.findTopByOrderByStartDesc().orElseThrow(() -> new AppException(AppError.NOT_FOUND_NO_VALID_MIGRATION_STATUS));
        return convert(dataMigrationStatus);
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
        details.put("IBAN_VALIDI_PER_PA", getTableMigrationStatus(migrationDetails.getIbanValidiPerPa()));
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
        details.put("WFESP_PLUGIN_CONN", getTableMigrationStatus(migrationDetails.getWfespPluginConn()));
        details.put("FTP_SERVER", getTableMigrationStatus(migrationDetails.getFtpServer()));
        details.put("PDD", getTableMigrationStatus(migrationDetails.getPdd()));
        details.put("GDE_CONFIG", getTableMigrationStatus(migrationDetails.getGdeConfig()));
        details.put("QUADRATURE_SCHED", getTableMigrationStatus(migrationDetails.getQuadratureSched()));

        // complete migration status
        return MigrationStatus.builder()
                .migrationStart(dataMigration.getStart().toString())//CommonUtils.toLocalDateTime(dataMigration.getStart()))
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
                .build();
    }
}
