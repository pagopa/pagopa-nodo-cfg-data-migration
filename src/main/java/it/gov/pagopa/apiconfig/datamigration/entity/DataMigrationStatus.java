package it.gov.pagopa.apiconfig.datamigration.entity;

import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DataMigrationStatus implements Serializable {

    private MigrationStepStatus status;

    private Timestamp start;

    private Timestamp end;
}