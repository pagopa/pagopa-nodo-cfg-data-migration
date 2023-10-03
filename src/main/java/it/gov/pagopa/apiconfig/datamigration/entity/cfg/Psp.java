package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Table(name = "PSP")
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Psp {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "ID_PSP", nullable = false, length = 35)
    private String idPsp;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @Column(name = "ABI", length = 5)
    private String abi;

    @Column(name = "BIC", length = 11)
    private String bic;

    @Column(name = "DESCRIZIONE", length = 70)
    private String descrizione;

    @Column(name = "RAGIONE_SOCIALE", length = 70)
    private String ragioneSociale;

    @Column(name = "FK_INT_QUADRATURE")
    private Long fkIntQuadrature;

    @Column(name = "CODICE_MYBANK", length = 35)
    private String codiceMybank;

    @Column(name = "MARCA_BOLLO_DIGITALE", nullable = false)
    private Integer marcaBolloDigitale;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "AGID_PSP", nullable = false)
    private Boolean agidPsp;

    @Column(name = "CODICE_FISCALE", length = 16)
    private String codiceFiscale;

    @Column(name = "VAT_NUMBER", length = 20)
    private String vatNumber;
}
