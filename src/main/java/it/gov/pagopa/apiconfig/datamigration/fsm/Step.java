package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;

import java.util.concurrent.Callable;

public abstract class Step implements Callable<State> {

    protected FSMSharedState sharedState;

    public abstract void executeStep() throws MigrationStepException;

    public abstract State getNextState();

    @Override
    public State call() throws MigrationStepException {
        executeStep();
        return getNextState();
    }

    public void attachSharedState(FSMSharedState fsm) {
        this.sharedState = fsm;
    }
}
