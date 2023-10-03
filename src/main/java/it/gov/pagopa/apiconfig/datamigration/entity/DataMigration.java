package it.gov.pagopa.apiconfig.datamigration.entity;

import it.gov.pagopa.apiconfig.datamigration.util.JsonBConverter;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Table(name = "CFG_DATA_MIGRATION")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DataMigration {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "START_EXEC", nullable = false)
    private Timestamp start;

    @Column(name = "RESTART_EXEC")
    private Timestamp restart;

    @Column(name = "END_EXEC")
    private Timestamp end;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "LAST_EXECUTED_STEP", nullable = false)
    private String lastExecutedStep;

    @Column(name = "DETAIL", nullable = false, columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = JsonBConverter.class)
    private DataMigrationDetails details;
}
