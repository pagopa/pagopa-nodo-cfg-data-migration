package it.gov.pagopa.nodo.datamigration.exception.migration;

public class MigrationTruncateAllTablesException extends MigrationStepException {

    public MigrationTruncateAllTablesException(Throwable cause) {
        super("Error while trying to truncate all tables.", cause);
    }
}
