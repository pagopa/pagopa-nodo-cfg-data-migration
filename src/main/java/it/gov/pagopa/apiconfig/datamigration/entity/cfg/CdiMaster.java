package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "CDI_MASTER")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CdiMaster {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_INFORMATIVA_PSP", nullable = false, length = 35)
    private String idInformativaPsp;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private Timestamp dataInizioValidita;

    @Column(name = "DATA_PUBBLICAZIONE")
    private Timestamp dataPubblicazione;

    @Column(name = "LOGO_PSP")
    @ToString.Exclude
    private byte[] logoPsp;

    @Column(name = "URL_INFORMAZIONI_PSP")
    private String urlInformazioniPsp;

    @Column(name = "MARCA_BOLLO_DIGITALE", nullable = false)
    private Integer marcaBolloDigitale;

    @Column(name = "STORNO_PAGAMENTO", nullable = false)
    private Integer stornoPagamento;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PSP", nullable = false)
    private Psp fkPsp;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_BINARY_FILE", nullable = false)
    private BinaryFile fkBinaryFile;

    @Column(name = "VERSIONE", length = 35)
    private String versione;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fkCdiMaster", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CdiDetail> cdiDetail;
}
