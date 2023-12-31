package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("ERROR")
public class ErrorStep extends Step {

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
