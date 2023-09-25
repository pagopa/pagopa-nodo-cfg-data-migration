package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public abstract class Step implements Callable<StepName> {

    protected FSMSharedState sharedState;

    public abstract void executeStep() throws MigrationStepException;

    public abstract StepName getNextState();

    public abstract String getStepName();

    @Override
    public StepName call() throws MigrationStepException {
        long startTime = System.currentTimeMillis();
        StepName nextState = getNextState();
        log.info(String.format("The step [%s] is starting its execution.", getStepName()));
        try {
            executeStep();
        } catch (MigrationInterruptedStepException e) {
            log.info(String.format("The step [%s] is interrupted gracefully. Next step will be END step.", getStepName()));
            nextState = StepName.END;
        } catch (MigrationStepException e) {
            log.error(String.format("The step [%s] is in error. Next step will be ERROR step.", getStepName()));
            nextState = StepName.ERROR;
        }
        log.info(String.format("The step [%s] ended its execution in [%d] ms.", getStepName(), (System.currentTimeMillis() - startTime)));
        return nextState;
    }

    public void attachSharedState(FSMSharedState fsm) {
        this.sharedState = fsm;
    }
}
