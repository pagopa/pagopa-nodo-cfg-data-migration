package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.AppError;
import it.gov.pagopa.apiconfig.datamigration.exception.AppException;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import it.gov.pagopa.apiconfig.datamigration.util.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FSMExecutor {

    @Autowired
    private CfgDataMigrationRepository cfgDataMigrationRepo;

    @Autowired
    private Map<String, Step> steps;

    @Getter
    private final FSMSharedState sharedState;

    private StepName currentStep;


    public FSMExecutor() {
        this.sharedState = new FSMSharedState();
    }

    private void execute() {
        while (this.sharedState.isInLock() && this.currentStep != null) {
            Step currentStepExecutor = this.steps.get(currentStep.toString());
            currentStepExecutor.attachSharedState(sharedState, cfgDataMigrationRepo);
            this.currentStep =  currentStepExecutor.call();
        }
    }

    public void start() {
        start(StepName.START);
    }

    public void start(StepName startingStep) {
        // check if status is in lock
        if (this.sharedState.isInLock()) {
            throw new AppException(AppError.STATUS_ALREADY_LOCKED);
        }
        this.sharedState.lock();
        this.currentStep = startingStep;
        execute();
    }

    public StepName restart() {
        // check if status is in lock
        if (this.sharedState.isInLock()) {
            throw new AppException(AppError.STATUS_ALREADY_LOCKED);
        }
        // check if the last migration exists and is in a restartable status
        DataMigration dataMigration = getLastMigrationStatus();
        StepName lastExecutedStep = StepName.valueOf(dataMigration.getLastExecutedStep());
        if (MigrationStepStatus.IN_PROGRESS.toString().equals(dataMigration.getStatus())) {
            throw new AppException(AppError.MIGRATION_ALREADY_IN_PROGRESS);
        } else if (MigrationStepStatus.COMPLETED.toString().equals(dataMigration.getStatus())) {
            throw new AppException(AppError.MIGRATION_ALREADY_COMPLETED);
        } else if (Constants.STATUS_NOT_RESTARTABLE.contains(lastExecutedStep)) {
            throw new AppException(AppError.MIGRATION_NOT_RESTARTABLE);
        }
        // lock the state and update the status
        dataMigration.setRestart(CommonUtils.now());
        dataMigration.setStatus(MigrationStepStatus.IN_PROGRESS.toString());
        cfgDataMigrationRepo.saveAndFlush(dataMigration);
        // update the FSM state with the ID of the saved state
        this.sharedState.resetStates();
        this.sharedState.setDataMigrationStateId(dataMigration.getId());
        return lastExecutedStep;
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

    public DataMigration getLastMigrationStatus() {
        return cfgDataMigrationRepo.findTopByOrderByStartDesc().orElseThrow(() -> new AppException(AppError.NOT_FOUND_NO_VALID_MIGRATION_STATUS));
    }
}
