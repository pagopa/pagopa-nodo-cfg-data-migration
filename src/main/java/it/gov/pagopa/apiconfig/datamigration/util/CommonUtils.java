package it.gov.pagopa.apiconfig.datamigration.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommonUtils {

    private CommonUtils() {}

    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}
