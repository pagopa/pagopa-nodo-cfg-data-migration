package it.gov.pagopa.nodo.datamigration.exception.migration;

public class MigrationStatusSavingException extends MigrationStepException {

    public MigrationStatusSavingException(Throwable cause) {
        super("Error while saving the migration status.", cause);
    }
}
