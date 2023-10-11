package it.gov.pagopa.nodo.datamigration.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommonUtils {

    private CommonUtils() {}

    public static Timestamp now() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Long getElapsedTime(Timestamp start, Timestamp end) {
        return end.getTime() - start.getTime();
    }
}
