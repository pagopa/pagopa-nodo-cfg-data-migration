package it.gov.pagopa.apiconfig.datamigration.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DataMigrationDetails implements Serializable {

    private String lastExecutedStep;

    private DataMigrationStatus intermediariPa;

    private DataMigrationStatus pa;

    private DataMigrationStatus stazioni;

    // TODO add all other tables
}
