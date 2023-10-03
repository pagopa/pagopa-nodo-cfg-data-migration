package it.gov.pagopa.nodo.datamigration.exception.migration;

public class DatabaseConnectionException extends MigrationStepException {

    public DatabaseConnectionException(String db) {
        super(String.format("Error while trying to check the connection of DB %s. Cannot connect correctly and execute the migration", db));
    }
}
