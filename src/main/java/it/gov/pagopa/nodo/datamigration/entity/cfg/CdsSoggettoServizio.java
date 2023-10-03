package it.gov.pagopa.nodo.datamigration.entity.cfg;

import it.gov.pagopa.nodo.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CDS_SOGGETTO_SERVIZIO")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CdsSoggettoServizio {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "FK_CDS_SOGGETTO", nullable = false)
    private Integer fkCdsSoggetto;

    @Column(name = "FK_CDS_SERVIZIO", nullable = false)
    private Integer fkCdsServizio;

    @Column(name = "STAZIONE")
    private Integer fkStazione;

    @Column(name = "ID_SOGGETTO_SERVIZIO")
    private String idSoggettoServizio;

    @Column(name = "DESCRIZIONE_SERVIZIO")
    private String descrizioneServizio;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private ZonedDateTime dataInizioValidita;

    @Column(name = "DATA_FINE_VALIDITA")
    private ZonedDateTime dataFineValidita;

    @Column(name = "COMMISSIONE")
    @Convert(converter = YesNoConverter.class)
    private Boolean commissione;
}
