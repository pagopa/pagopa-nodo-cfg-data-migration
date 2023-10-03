package it.gov.pagopa.nodo.datamigration.exception.migration;

public class MigrationStepException extends Exception {

    public MigrationStepException() {
        super();
    }

    public MigrationStepException(String message) {
        super(message);
    }

    public MigrationStepException(String message, Throwable cause) {
        super(message, cause);
    }
}
