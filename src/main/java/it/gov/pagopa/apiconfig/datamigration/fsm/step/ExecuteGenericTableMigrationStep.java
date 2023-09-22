package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.fsm.State;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteGenericTableMigrationStep extends Step {

    @Override
    public void executeStep() throws MigrationStepException {
        try {
            int cycle = 0;
            while (cycle < 10) {
                cycle++;
                if (this.sharedState.isInLock()) {
                    this.sharedState.setPersistingData(true);
                    log.info("Executing Generic-table migration step, simulating persisting page {}...", cycle);
                    Thread.sleep(1000);
                    this.sharedState.setPersistingData(false);
                } else {
                    throw new MigrationInterruptedStepException();
                }
            }
        } catch (InterruptedException e) {
            log.error("TEMP ERROR LOG", e);
            throw new MigrationStepException();
        }
    }

    @Override
    public State getNextState() {
        return State.END;
    }
}
