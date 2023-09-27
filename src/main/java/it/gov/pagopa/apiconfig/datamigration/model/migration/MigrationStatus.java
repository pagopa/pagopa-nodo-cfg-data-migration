package it.gov.pagopa.apiconfig.datamigration.model.migration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MigrationStatus implements Serializable {

    @JsonProperty("migration_start")
    private String migrationStart;

    @JsonProperty("elapsed_time")
    private Long elapsedTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("details")
    private Map<String, TableMigrationStatus> details;
}
