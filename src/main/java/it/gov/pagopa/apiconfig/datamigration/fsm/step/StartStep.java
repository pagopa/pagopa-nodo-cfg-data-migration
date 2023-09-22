package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.fsm.State;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartStep extends Step {

    @Override
    public void executeStep() throws MigrationStepException {
        try {
            log.info("Executing start step...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("TEMP ERROR LOG", e);
            throw new MigrationStepException();
        }
    }

    @Override
    public State getNextState() {
        return State.EXECUTE_GENERIC_TABLE_MIGRATION;
    }
}
