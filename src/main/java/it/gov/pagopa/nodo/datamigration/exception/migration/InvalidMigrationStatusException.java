package it.gov.pagopa.nodo.datamigration.exception.migration;

public class InvalidMigrationStatusException extends MigrationStepException {

    public InvalidMigrationStatusException() {
        super("Error while saving the migration status. No valid migration status found.");
    }
}
