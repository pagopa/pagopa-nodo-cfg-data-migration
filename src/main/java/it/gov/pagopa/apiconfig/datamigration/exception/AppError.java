package it.gov.pagopa.apiconfig.datamigration.exception;

import it.gov.pagopa.apiconfig.datamigration.util.Constants;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum AppError {

    STATUS_ALREADY_LOCKED(HttpStatus.CONFLICT, Constants.STATUS_CONFLICT, "Error while trying to lock the migration status. The status is already locked."),

    STATUS_NOT_LOCKED(HttpStatus.CONFLICT, Constants.STATUS_CONFLICT, "Error while trying to unlock the migration status. The status is not locked."),

    FORCE_STOP_ALREADY_REQUESTED(HttpStatus.CONFLICT, Constants.STATUS_CONFLICT, "Error while trying to stop the migration process. A graceful stop was already requested."),

    NOT_FOUND_NO_VALID_MIGRATION_STATUS(HttpStatus.NOT_FOUND, "Not found", "Error while reading the status of the last execution. No valid migration was executed before."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Something was wrong");

    public final HttpStatus httpStatus;
    public final String title;
    public final String details;


    AppError(HttpStatus httpStatus, String title, String details) {
        this.httpStatus = httpStatus;
        this.title = title;
        this.details = details;
    }
}


