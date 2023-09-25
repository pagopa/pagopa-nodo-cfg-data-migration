package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.CfgDataMigrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("END")
public class EndStep extends Step {

    @Autowired
    private CfgDataMigrationRepository repository;

    @Override
    public void executeStep() throws MigrationStepException {
        // save migration status
        try {
            // repository.readLastMigration(); // TODO read data about this migration
            // update end_date=now(), if this.sharedState.isBlocked() then status=BLOCKED : status=COMPLETED
            // repository.save(dataMigration); // TODO set the correct mode for save data
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
        return StepName.END.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return null;
    }
}
