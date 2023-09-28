package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.starter.util.YesNoConverter;
import lombok.*;

import javax.persistence.*;

@Table(name = "PA_STAZIONE_PA", uniqueConstraints = {@UniqueConstraint(columnNames = {"FK_PA", "FK_STAZIONE"})})
@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaStazionePa {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "PROGRESSIVO")
    private Long progressivo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PA", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pa pa;

    @Column(name = "FK_PA", nullable = false, insertable = false, updatable = false)
    private Long fkPa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_STAZIONE", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Stazioni fkStazione;

    @Column(name = "AUX_DIGIT")
    private Long auxDigit;

    @Column(name = "SEGREGAZIONE")
    private Long segregazione;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "QUARTO_MODELLO", nullable = false)
    private Boolean quartoModello = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "BROADCAST", nullable = false)
    private Boolean broadcast = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "PAGAMENTO_SPONTANEO", nullable = false)
    private Boolean pagamentoSpontaneo = false;
}
