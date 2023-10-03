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
@Table(name = "CDI_INFORMAZIONI_SERVIZIO")
public class CdiInformazioniServizio {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "CODICE_LINGUA", nullable = false, length = 2)
    private String codiceLingua;

    @Column(name = "DESCRIZIONE_SERVIZIO", nullable = false, length = 140)
    private String descrizioneServizio;

    @Column(name = "DISPONIBILITA_SERVIZIO", nullable = false, length = 140)
    private String disponibilitaServizio;

    @Column(name = "URL_INFORMAZIONI_CANALE")
    private String urlInformazioniCanale;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CDI_DETAIL", nullable = false)
    @ToString.Exclude
    private CdiDetail fkCdiDetail;

    @Column(name = "LIMITAZIONI_SERVIZIO", length = 140)
    private String limitazioniServizio;
}
