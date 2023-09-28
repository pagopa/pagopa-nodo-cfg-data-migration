package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStatusSavingException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationTruncateAllTablesException;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("START")
public class StartStep extends Step {

    @Override
    public void executeStep() throws MigrationStepException {
        // resetting flags and creating a new record in the CFG_DATA_MIGRATION table
        activateMigration();
        // deleting all data in all tables
        truncateAllTables();
    }

    @Override
    public StepName getNextState() {
        return StepName.EXECUTE_INTERMEDIARI_PA_TABLE_MIGRATION;
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
                            .ibanValidiPerPa(new DataMigrationStatus())
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
                            .wfespPluginConn(new DataMigrationStatus())
                            .ftpServer(new DataMigrationStatus())
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
            // deleteAndFlush(quadratureSchedRepo)
            // deleteAndFlush(gdeConfigRepo);
            // deleteAndFlush(pddRepo);
            // deleteAndFlush(ftpServerRepo);
            // deleteAndFlush(wfespPluginConnRepo);
            // deleteAndFlush(configurationKeysRepo);
            // deleteAndFlush(cdsSoggettoServizioRepo);
            // deleteAndFlush(cdsServizioRepo);
            // TODO continue with other tables
        } catch (DataAccessException e) {
            throw new MigrationTruncateAllTablesException(e);
        }
    }

    private void deleteAndFlush(JpaRepository repo) {
        repo.deleteAll();
        repo.flush();
    }
}
