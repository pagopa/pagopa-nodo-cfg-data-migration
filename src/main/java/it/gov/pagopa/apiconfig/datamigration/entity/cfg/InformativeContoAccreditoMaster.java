package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "INFORMATIVE_CONTO_ACCREDITO_MASTER")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class InformativeContoAccreditoMaster {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private Timestamp dataInizioValidita;

    @Column(name = "DATA_PUBBLICAZIONE")
    private Timestamp dataPubblicazione;

    @Column(name = "ID_INFORMATIVA_CONTO_ACCREDITO_PA", nullable = false, length = 35)
    private String idInformativaContoAccreditoPa;

    @Column(name = "RAGIONE_SOCIALE", length = 70)
    private String ragioneSociale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PA")
    @ToString.Exclude
    private Pa fkPa;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_BINARY_FILE")
    @ToString.Exclude
    private BinaryFile fkBinaryFile;

    @Column(name = "VERSIONE", length = 35)
    private String versione;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "fkInformativaContoAccreditoMaster",
            cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<InformativeContoAccreditoDetail> icaDetail;
}