package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("ERROR")
public class ErrorStep extends Step {

    @Autowired
    private CfgDataMigrationRepository cfgDataMigrationRepo;

    @Override
    public void executeStep() {
        // save migration status
        try {
            Optional<DataMigration> dataMigrationOpt = cfgDataMigrationRepo.findById(this.sharedState.getDataMigrationStateId());
            if (dataMigrationOpt.isPresent()) {
                DataMigration dataMigration = dataMigrationOpt.get();
                dataMigration.setStatus(MigrationStepStatus.FAILED.toString());
                dataMigration.setEnd(CommonUtils.now());
                cfgDataMigrationRepo.saveAndFlush(dataMigration);
            } else {
                log.error("Error while saving migration state in ERROR step. The record in CFG_DATA_MIGRATION is not present and cannot be updated.");
            }
        } catch (DataAccessException e) {
            log.error("Error while saving migration state in ERROR step.", e);
        }
        // resetting migration flags
        this.sharedState.resetStates();
        this.sharedState.unlock();
    }

    @Override
    public StepName getNextState() {
        return null;
    }

    @Override
    public String getStepName() {
        return StepName.ERROR.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return null;
    }
}
