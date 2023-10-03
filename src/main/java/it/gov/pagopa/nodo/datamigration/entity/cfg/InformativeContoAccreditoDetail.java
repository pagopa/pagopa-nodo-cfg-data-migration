package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

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

    @Column(name = "FK_INFORMATIVA_CONTO_ACCREDITO_MASTER", length = 35)
    private Long fkInformativaContoAccreditoMaster;

    @Column(name = "ID_MERCHANT", length = 15)
    private String idMerchant;

    @Column(name = "CHIAVE_AVVIO")
    private String chiaveAvvio;

    @Column(name = "CHIAVE_ESITO")
    private String chiaveEsito;

    @Column(name = "ID_BANCA_SELLER", length = 50)
    private String idBancaSeller;
}
