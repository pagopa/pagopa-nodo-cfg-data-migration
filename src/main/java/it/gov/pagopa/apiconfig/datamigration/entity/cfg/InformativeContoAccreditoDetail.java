package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;

@Table(name = "INFORMATIVE_CONTO_ACCREDITO_DETAIL")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
public class InformativeContoAccreditoDetail {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "IBAN_ACCREDITO", length = 35)
    private String ibanAccredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_INFORMATIVA_CONTO_ACCREDITO_MASTER")
    @ToString.Exclude
    private InformativeContoAccreditoMaster fkInformativaContoAccreditoMaster;

    @Column(name = "ID_MERCHANT", length = 15)
    private String idMerchant;

    @Column(name = "CHIAVE_AVVIO")
    private String chiaveAvvio;

    @Column(name = "CHIAVE_ESITO")
    private String chiaveEsito;

    @Column(name = "ID_BANCA_SELLER", length = 50)
    private String idBancaSeller;
}