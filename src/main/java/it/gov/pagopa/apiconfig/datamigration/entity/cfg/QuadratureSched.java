package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Table(name = "QUADRATURE_SCHED")
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class QuadratureSched {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "ID_SOGGETTO", length = 35)
    private String idSoggetto;

    @Column(name = "MODALITA", length = 35)
    private String modalita;

    @Column(name = "TIPO_QUAD", length = 35)
    private String tipoQuad;

    @Column(name = "DAYS_OF_THE_WEEK_CODE", length = 35)
    private String daysOfTheWeekCode;

    @Column(name = "JOB_START_DATE")
    private Timestamp jobStartDate;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ON_CLICK", nullable = false)
    private Boolean onClick;

    @Column(name = "JOB_END_HOUR")
    private Timestamp jobEndHour;

    @Column(name = "JOB_START_HOUR")
    private Timestamp jobStartHour;

    @Column(name = "TIMESTAMP_LAST_ACTION")
    private Timestamp timestampLastAction;

    @Column(name = "TIMESTAMP_END")
    private Timestamp timestampEnd;

    @Column(name = "TIMESTAMP_BEGIN")
    private Timestamp timestampBegin;

    @Column(name = "QUAD_LAST_DATE")
    private Timestamp quadLastDate;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "TYPE_INIZIO_GIORNATA", nullable = false)
    private Boolean typeInizioGiornata;
}
