package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanAttributes;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.repository.oracle.IbanAttributesSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.IbanAttributesDestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("EXECUTE_IBAN_ATTRIBUTES_TABLE_MIGRATION")
public class ExecuteIbanAttributesTableMigrationStep extends Step {

    @Value("${step.iban_attributes.batch.size}")
    private Integer PAGE_SIZE;

    @Autowired
    IbanAttributesSrcRepository srcRepo;

    @Autowired
    IbanAttributesDestRepository destRepo;

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
                Page<IbanAttributes> pagedEntities = srcRepo.findAll(pageable);
                List<IbanAttributes> entities = pagedEntities.getContent();
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
        return StepName.EXECUTE_IBAN_MASTER_TABLE_MIGRATION;
    }

    @Override
    public String getStepName() {
        return StepName.EXECUTE_IBAN_ATTRIBUTES_TABLE_MIGRATION.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return details.getIbanAttributes();
    }
}
