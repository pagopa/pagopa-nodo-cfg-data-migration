package it.gov.pagopa.apiconfig.datamigration.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String API_START_OK = "Migration started successfully.";
    public static final String API_STOP_OK = "he migration will be terminated gracefully. Please, wait for the termination and saving of the state.";
    public static final String API_RESTART_OK = "Migration restarted successfully.";
    public static final String STATUS_CONFLICT = "Status Conflict";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";

}
