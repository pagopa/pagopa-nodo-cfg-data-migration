package it.gov.pagopa.nodo.datamigration.fsm;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.InvalidMigrationStatusException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationInterruptedStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.concurrent.Callable;

@Slf4j
public abstract class Step implements Callable<StepName> {

    protected FSMSharedState sharedState;

    protected CfgDataMigrationRepository cfgDataMigrationRepo;

    public abstract void executeStep() throws MigrationStepException;

    public abstract StepName getNextState();

    public abstract String getStepName();

    public abstract DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details);

    @Override
    public StepName call() {
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

    public void attachSharedState(FSMSharedState fsm, CfgDataMigrationRepository repository) {
        this.sharedState = fsm;
        this.cfgDataMigrationRepo = repository;
    }

    protected boolean canContinueReadPages(Pageable pageable) {
        return !this.sharedState.isBlockRequested() && this.sharedState.isInLock() && pageable.isPaged();
    }

    protected void checkExecutionBlock(CfgDataMigrationRepository cfgDataMigrationRepo, boolean updateStatus) throws MigrationInterruptedStepException, InvalidMigrationStatusException {
        if (this.sharedState.isBlockRequested()) {
            if (updateStatus) {
                updateDataMigrationStatusOnBlock(cfgDataMigrationRepo);
            }
            throw new MigrationInterruptedStepException();
        }
    }

    public void updateDataMigrationStatusOnStart(CfgDataMigrationRepository cfgDataMigrationRepo) throws InvalidMigrationStatusException {
        updateDataMigrationStatus(cfgDataMigrationRepo, MigrationStepStatus.IN_PROGRESS, CommonUtils.now(), null, 0);
    }

    protected void updateDataMigrationStatusOnEnd(CfgDataMigrationRepository cfgDataMigrationRepo, long records) throws InvalidMigrationStatusException {
        updateDataMigrationStatus(cfgDataMigrationRepo, MigrationStepStatus.COMPLETED, null, CommonUtils.now(), records);
    }

    protected void updateDataMigrationStatusOnStepEnd(CfgDataMigrationRepository cfgDataMigrationRepo, long records) throws InvalidMigrationStatusException {
        if (this.sharedState.isBlockRequested()) {
            updateDataMigrationStatusOnBlock(cfgDataMigrationRepo);
        } else {
            updateDataMigrationStatusOnEnd(cfgDataMigrationRepo, records);
        }
    }

    public void updateDataMigrationStatusOnFailure(CfgDataMigrationRepository cfgDataMigrationRepo) throws InvalidMigrationStatusException {
        updateDataMigrationStatus(cfgDataMigrationRepo, MigrationStepStatus.FAILED, null, CommonUtils.now(), 0);
    }

    public void updateDataMigrationStatusOnBlock(CfgDataMigrationRepository cfgDataMigrationRepo) throws InvalidMigrationStatusException {
        updateDataMigrationStatus(cfgDataMigrationRepo, MigrationStepStatus.BLOCKED, null, CommonUtils.now(), 0);
    }

    protected void updateDataMigrationStatus(CfgDataMigrationRepository cfgDataMigrationRepo, MigrationStepStatus stepStatus, Timestamp start, Timestamp end, long records) throws InvalidMigrationStatusException {
        DataMigration dataMigration = cfgDataMigrationRepo.findById(this.sharedState.getDataMigrationStateId()).orElseThrow(InvalidMigrationStatusException::new);
        dataMigration.setLastExecutedStep(getStepName());
        DataMigrationStatus migrationStatus = getDataMigrationStatus(dataMigration.getDetails());
        migrationStatus.setStatus(stepStatus.toString());
        migrationStatus.setRecords(records);
        if (start != null) {
            migrationStatus.setStart(start);
        }
        if (end != null) {
            migrationStatus.setEnd(end);
        }
        cfgDataMigrationRepo.saveAndFlush(dataMigration);
    }
}
