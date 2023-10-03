package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CDS_SERVIZIO")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CdsServizio {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_SERVIZIO", nullable = false)
    private String idServizio;

    @Column(name = "DESCRIZIONE_SERVIZIO")
    private String descrizioneServizio;

    @Column(name = "XSD_RIFERIMENTO", nullable = false)
    private String xsdRiferimento;

    @Column(name = "VERSIONE", nullable = false)
    private Long version;

    @Column(name = "CATEGORIA_ID", nullable = false, insertable = false, updatable = false)
    private Long categoriaId;
}
