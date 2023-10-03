package it.gov.pagopa.nodo.datamigration.util;

import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class Constants {

    public static final String API_START_OK = "Migration started successfully.";
    public static final String API_STOP_OK = "The migration will be terminated gracefully. Please, wait for the termination and saving of the state.";
    public static final String API_RESTART_OK = "Migration restarted successfully.";
    public static final String STATUS_CONFLICT = "Status Conflict";

    public static final Set<StepName> STATUS_NOT_RESTARTABLE = Set.of(StepName.START, StepName.END);
    public static final String HEADER_REQUEST_ID = "X-Request-Id";

}
