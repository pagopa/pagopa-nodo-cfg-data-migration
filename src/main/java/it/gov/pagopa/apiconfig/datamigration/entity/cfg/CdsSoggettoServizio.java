package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.starter.util.YesNoConverter;
import lombok.*;

import javax.persistence.*;
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

    @Column(name = "FK_CDS_SOGGETTO", nullable = false, insertable = false, updatable = false)
    private String fkCdsSoggetto;

    @Column(name = "FK_CDS_SERVIZIO", nullable = false, insertable = false, updatable = false)
    private String fkCdsServizio;

    @Column(name = "STAZIONE", nullable = true, insertable = false, updatable = false)
    private String fkStazione;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STAZIONE", nullable = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PaStazionePa stazionePa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CDS_SOGGETTO", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CdsSoggetto soggetto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CDS_SERVIZIO", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CdsServizio servizio;
}
