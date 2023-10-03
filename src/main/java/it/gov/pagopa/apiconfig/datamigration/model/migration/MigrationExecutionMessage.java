package it.gov.pagopa.nodo.datamigration.model.migration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MigrationExecutionMessage implements Serializable {
    private String message;
}
