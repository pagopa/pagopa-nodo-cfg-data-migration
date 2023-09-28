package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.apiconfig.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.oracle.OracleDBSystemRepository;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.PostgresDBSystemRepository;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("END")
public class EndStep extends Step {

    @Autowired
    private OracleDBSystemRepository oracleSystemRepo;

    @Autowired
    private PostgresDBSystemRepository postgresSystemRepo;

    @Override
    public void executeStep() {
        // save migration status
        try {
            Optional<DataMigration> dataMigrationOpt = cfgDataMigrationRepo.findById(this.sharedState.getDataMigrationStateId());
            if (dataMigrationOpt.isPresent()) {
                // update the state with completed state
                DataMigration dataMigration = dataMigrationOpt.get();
                dataMigration.setStatus(this.sharedState.isBlockRequested() ? MigrationStepStatus.BLOCKED.toString() : MigrationStepStatus.COMPLETED.toString());
                if (!this.sharedState.isBlockRequested()) {
                    dataMigration.setLastExecutedStep(StepName.END.toString());
                }
                dataMigration.setEnd(CommonUtils.now());
                cfgDataMigrationRepo.saveAndFlush(dataMigration);
                // update sequences
                updateSequenceLastValue();
            } else {
                log.error("Error while saving migration state in END step. The record in CFG_DATA_MIGRATION is not present and cannot be updated.");
            }
        } catch (DataAccessException e) {
            log.error("Error while saving migration state in END step.", e);
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

    private void updateSequenceLastValue() {
        Long sequenceLastNumber = oracleSystemRepo.readHibernateSequence();
        postgresSystemRepo.updateHibernateSequence(sequenceLastNumber);
    }
}
