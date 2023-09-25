package it.gov.pagopa.apiconfig.datamigration.fsm.step;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStatusSavingException;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.migration.MigrationTruncateAllTablesException;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.CfgDataMigrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service("START")
public class StartStep extends Step {

    //@Autowired
    private CfgDataMigrationRepository repository;

    @Override
    public void executeStep() throws MigrationStepException {

        // resetting flags and creating a new record in the CFG_DATA_MIGRATION table
        activateMigration();

        truncateAllTables();

    }

    @Override
    public StepName getNextState() {
        return StepName.EXECUTE_GENERIC_TABLE_MIGRATION;
    }

    @Override
    public String getStepName() {
        return StepName.START.toString();
    }

    private void activateMigration() throws MigrationStatusSavingException {
        // resetting migration flags
        this.sharedState.resetStates();
        this.sharedState.lock();
        // save migration status
        try {
            DataMigration dataMigration = DataMigration.builder()
                    .id(UUID.randomUUID().toString())
                    .start(Timestamp.valueOf(LocalDateTime.now()))
                    .status(MigrationStepStatus.IN_PROGRESS.toString())
                    .details(null) // TODO add generation of the DataMigrationDetail object
                    .build();
            //repository.save(dataMigration); // TODO set the correct mode for save data
        } catch (DataAccessException e) {
            throw new MigrationStatusSavingException(e);
        }
    }

    private void truncateAllTables() throws MigrationTruncateAllTablesException {
        try {
            // TODO set the correct mode for truncate all tables
        } catch (DataAccessException e) {
            throw new MigrationTruncateAllTablesException(e);
        }
    }
}
