package it.gov.pagopa.nodo.datamigration.model.migration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableMigrationStatus implements Serializable {

    @JsonProperty("status")
    private String status;

    @JsonProperty("start")
    private String start;

    @JsonProperty("elapsed_time")
    private Long elapsedTime;

    @JsonProperty("records")
    private Long records;
}
