package it.gov.pagopa.apiconfig.datamigration.model;

import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class MigrationStatus {
    private Instant migrationStart;
    private long elapsedMillis;
    private String status;
    private Map<String, StepName> tableStatus; //si può popolare la table con default: TO DO se non è stata toccata
}
