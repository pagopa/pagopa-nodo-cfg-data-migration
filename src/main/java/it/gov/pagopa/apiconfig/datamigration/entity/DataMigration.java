package it.gov.pagopa.apiconfig.datamigration.entity;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column(name = "START", nullable = false)
    private Timestamp start;

    @Column(name = "END")
    private Timestamp end;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "DETAIL", nullable = false)
    private DataMigrationDetails details;
}
