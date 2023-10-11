package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "CDI_FASCIA_COSTO_SERVIZIO")
public class CdiFasciaCostoServizio {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "IMPORTO_MINIMO", nullable = false)
    private Double importoMinimo;

    @Column(name = "IMPORTO_MASSIMO", nullable = false)
    private Double importoMassimo;

    @Column(name = "COSTO_FISSO", nullable = false)
    private Double costoFisso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CDI_DETAIL", nullable = false)
    @ToString.Exclude
    private CdiDetail fkCdiDetail;

    @Column(name = "VALORE_COMMISSIONE")
    private Double valoreCommissione;

    @Column(name = "CODICE_CONVENZIONE", length = 35)
    private String codiceConvenzione;
}
