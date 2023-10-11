package it.gov.pagopa.nodo.datamigration.fsm.step;

import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import it.gov.pagopa.nodo.datamigration.entity.cfg.CanaleTipoVersamento;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationStepException;
import it.gov.pagopa.nodo.datamigration.fsm.Step;
import it.gov.pagopa.nodo.datamigration.repository.oracle.CanaleTipoVersamentoSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CanaleTipoVersamentoDestRepository;
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
@Service("EXECUTE_CANALE_TIPO_VERSAMENTO_TABLE_MIGRATION")
public class ExecuteCanaleTipoVersamentoTableMigrationStep extends Step {

    @Value("${step.canale_tipo_versamento.batch.size}")
    private Integer PAGE_SIZE;

    @Autowired
    CanaleTipoVersamentoSrcRepository srcRepo;

    @Autowired
    CanaleTipoVersamentoDestRepository destRepo;

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
                Page<CanaleTipoVersamento> pagedEntities = srcRepo.findAll(pageable);
                List<CanaleTipoVersamento> entities = pagedEntities.getContent();
                recordCounter += entities.size();
                destRepo.saveAllAndFlush(entities);
                pageable = pagedEntities.nextPageable();
            } while(canContinueReadPages(pageable));

            // ending migration step: update migration status
            updateDataMigrationStatusOnStepEnd(cfgDataMigrationRepo,recordCounter);
            checkExecutionBlock(cfgDataMigrationRepo, false);

        } catch (DataAccessException e) {
            updateDataMigrationStatusOnFailure(cfgDataMigrationRepo);
            throw new MigrationErrorOnStepException(getStepName(), e);
        }
    }

    @Override
    public StepName getNextState() {
        return StepName.EXECUTE_PSP_CANALE_TIPO_VERSAMENTO_TABLE_MIGRATION;
    }

    @Override
    public String getStepName() {
        return StepName.EXECUTE_CANALE_TIPO_VERSAMENTO_TABLE_MIGRATION.toString();
    }

    @Override
    public DataMigrationStatus getDataMigrationStatus(DataMigrationDetails details) {
        return details.getCanaleTipoVersamento();
    }
}
