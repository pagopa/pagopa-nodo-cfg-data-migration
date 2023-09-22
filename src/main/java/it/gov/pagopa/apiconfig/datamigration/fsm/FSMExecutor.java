package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationInLockStateException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FSMExecutor {

    private FSMSharedState sharedState;

    private State currentStep;

    public FSMExecutor() {
        this.sharedState = new FSMSharedState();
    }

    public void execute() throws Exception {
        if (this.sharedState.isInLock()) {
            throw new MigrationInLockStateException();
        }
        this.sharedState.setInLock(true);
        while (this.sharedState.isInLock() && this.currentStep != null) {
            this.currentStep = this.currentStep.execute(sharedState);
        }
        this.sharedState.setInLock(false);
    }

    public void start() throws Exception {
        this.currentStep = State.START;
        execute();
    }

    public void restart() throws Exception {
        // TODO reload migration status from postgreSQL
        // TODO set currentStep with the IN_PROGRESS (if exists)
        execute();
    }

    public void forceStop() {
        this.sharedState.setInLock(false);
    }
}
