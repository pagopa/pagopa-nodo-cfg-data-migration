package it.gov.pagopa.apiconfig.datamigration.model;

import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MigrationStatus {
    private Instant migrationStart;
    private long elapsedMillis;
    private String status;
    private Map<String, StepName> tableStatus; //si può popolare la table con default: TO DO se non è stata toccata
}
