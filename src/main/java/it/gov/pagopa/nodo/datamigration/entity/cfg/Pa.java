package it.gov.pagopa.nodo.datamigration.entity.cfg;

import it.gov.pagopa.nodo.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Table(name = "PA")
    @Entity
    @Getter
    @Setter
    @ToString
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

public class Pa {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "ID_DOMINIO", unique = true)
    private String idDominio;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "RAGIONE_SOCIALE")
    private String ragioneSociale;

    @Column(name = "FK_INT_QUADRATURE")
    private Long fkIntQuadrature;

    @Column(name = "INDIRIZZO_DOMICILIO_FISCALE")
    private String indirizzoDomicilioFiscale;

    @Column(name = "CAP_DOMICILIO_FISCALE")
    @Size(max = 5)
    private String capDomicilioFiscale;

    @Column(name = "SIGLA_PROVINCIA_DOMICILIO_FISCALE")
    private String siglaProvinciaDomicilioFiscale;

    @Column(name = "COMUNE_DOMICILIO_FISCALE")
    private String comuneDomicilioFiscale;

    @Column(name = "DENOMINAZIONE_DOMICILIO_FISCALE")
    private String denominazioneDomicilioFiscale;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "PAGAMENTO_PRESSO_PSP")
    private Boolean pagamentoPressoPsp;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "RENDICONTAZIONE_FTP")
    private Boolean rendicontazioneFtp;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "RENDICONTAZIONE_ZIP")
    private Boolean rendicontazioneZip;

    @Column(name = "DESCRIZIONE")
    private String description;

    @Column(name = "CBILL")
    private String cbill;
}
