package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.QuadratureSched;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.repository.oracle.QuadratureSchedSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.QuadratureSchedDestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION")
public class ExecuteQuadratureSchedTableMigrationStep extends Step {

    private static final int PAGE_SIZE = 50;

    @Autowired
    QuadratureSchedSrcRepository srcRepo;

    @Autowired
    QuadratureSchedDestRepository destRepo;

    @Override
    public void executeStep() throws MigrationStepException {
        try {
            // starting migration step: update migration status
            updateDataMigrationStatusOnStart(cfgDataMigrationRepo);
            checkExecutionBlock(cfgDataMigrationRepo, true);

            // starting migration: read from source DB, then save on destination DB, until end or stop
            Pageable pageable = PageRequest.of(0, PAGE_SIZE);
            long recordCounter = 0;
            do {
                Page<QuadratureSched> pagedEntities = srcRepo.findAll(pageable);
                List<QuadratureSched> entities = pagedEntities.getContent();
                recordCounter += entities.size();
                destRepo.saveAllAndFlush(entities);
                pageable = pagedEntities.nextPageable();
            } while(canContinueReadPages(pageable));

            // ending migration step: update migration status
            updateDataMigrationStatusOnStepEnd(cfgDataMigrationRepo, recordCounter);
            checkExecutionBlock(cfgDataMigrationRepo, false);

        } catch (DataAccessException e) {
            updateDataMigrationStatusOnFailure(cfgDataMigrationRepo);
            throw new MigrationErrorOnStepException(getStepName(), e);
        }
    }

    @Override
    public StepName getNextState() {
        return StepName.EXECUTE_INTERMEDIARI_PA_TABLE_MIGRATION;
    }

    @Override
    public String getStepName() {
        return StepName.EXECUTE_QUADRATURE_SCHED_TABLE_MIGRATION.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return details.getQuadratureSched();
    }
}
