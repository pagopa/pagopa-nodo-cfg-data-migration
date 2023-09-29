package it.gov.pagopa.apiconfig.datamigration.entity;

import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DataMigrationStatus implements Serializable {

    private String status;

    private Timestamp start;

    private Timestamp end;

    private Long records;

    public DataMigrationStatus() {
        this.status = MigrationStepStatus.TODO.toString();
    }
}
