package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("EXECUTE_GENERIC_TABLE_MIGRATION")
public class ExecuteGenericTableMigrationStep extends Step {

    @Override
    public void executeStep() throws MigrationStepException {
        try {
            int cycle = 0;
            while (cycle < 10) {
                cycle++;
                if (!this.sharedState.isBlockRequested() && this.sharedState.isInLock()) {
                    this.sharedState.setPersistingData(true);
                    log.info("Executing Generic-table migration step, simulating persisting page {}...", cycle);
                    Thread.sleep(5000);
                    this.sharedState.setPersistingData(false);
                } else {
                    throw new MigrationInterruptedStepException();
                }
            }
        } catch (InterruptedException e) {
            log.error("TEMP ERROR LOG", e);
            throw new MigrationStepException("", e);
        }
    }

    @Override
    public StepName getNextState() {
        return StepName.END;
    }

    @Override
    public String getStepName() {
        return StepName.EXECUTE_GENERIC_TABLE_MIGRATION.toString();
    }
}
