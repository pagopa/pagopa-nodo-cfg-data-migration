package it.gov.pagopa.nodo.datamigration.exception.migration;

public class MigrationErrorOnStepException extends MigrationStepException {

    public MigrationErrorOnStepException(String stepName, Throwable cause) {
        super(String.format("Error while executing the step [%s].", stepName), cause);
    }
}
