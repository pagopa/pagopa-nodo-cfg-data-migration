package it.gov.pagopa.nodo.datamigration.util;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonUtilis {
    @Test
    void testNow() {
        Timestamp currentTimestamp = CommonUtils.now();
        Timestamp expectedTimestamp = Timestamp.valueOf(LocalDateTime.now());
        assertEquals(expectedTimestamp.getTime(), currentTimestamp.getTime(), 1000); // 1000 ms tolerance
    }

    @Test
    void testGetElapsedTime() {
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 0, 0, 0));
        Timestamp endTime = Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 0, 1, 0));
        Long elapsedTime = CommonUtils.getElapsedTime(startTime, endTime);
        Long expectedElapsedTime = 60_000L;
        assertEquals(expectedElapsedTime, elapsedTime);
    }
}
