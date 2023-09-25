package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.AppError;
import it.gov.pagopa.apiconfig.datamigration.exception.AppException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FSMExecutor {

    @Autowired
    private Map<String, Step> steps;

    private final FSMSharedState sharedState;

    private StepName currentStep;

    public FSMExecutor() {
        this.sharedState = new FSMSharedState();
    }

    private void execute() throws MigrationStepException {
        while (this.sharedState.isInLock() && this.currentStep != null) {
            Step currentStepExecutor = this.steps.get(currentStep.toString());
            currentStepExecutor.attachSharedState(sharedState);
            this.currentStep =  currentStepExecutor.call();
        }
    }

    public void start() throws Exception {
        // check if status is in lock
        if (this.sharedState.isInLock()) {
            throw new AppException(AppError.STATUS_ALREADY_LOCKED);
        }
        this.sharedState.lock();
        this.currentStep = StepName.START;
        execute();
    }

    public void restart() throws Exception {
        // check if status is in lock
        if (this.sharedState.isInLock()) {
            throw new AppException(AppError.STATUS_ALREADY_LOCKED);
        }
        this.sharedState.lock();

        // TODO reload migration status from postgreSQL
        // TODO set currentStep with the IN_PROGRESS status (if exists)
        execute();
    }

    public void forceStop() {
        if (this.sharedState.isBlockRequested()) {
            throw new AppException(AppError.FORCE_STOP_ALREADY_REQUESTED);
        }
        if (!this.sharedState.isInLock()) {
            throw new AppException(AppError.STATUS_NOT_LOCKED);
        }
        this.sharedState.requestBlock();
    }
}
