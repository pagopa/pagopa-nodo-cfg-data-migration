package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "CANALI_NODO")
@Builder
public class CanaliNodo {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "REDIRECT_IP", length = 100)
    private String redirectIp;

    @Column(name = "REDIRECT_PATH", length = 100)
    private String redirectPath;

    @Column(name = "REDIRECT_PORTA")
    private Long redirectPorta;

    @Column(name = "REDIRECT_QUERY_STRING")
    private String redirectQueryString;

    @Column(name = "MODELLO_PAGAMENTO", nullable = false)
    private String modelloPagamento;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "MULTI_PAYMENT", nullable = false)
    private Boolean multiPayment = false;

    @Column(name = "RAGIONE_SOCIALE", length = 35)
    private String ragioneSociale;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "RPT_RT_COMPLIANT", nullable = false)
    private Boolean rptRtCompliant = false;

    @Column(name = "WSAPI", length = 15)
    private String wsapi;

    @Column(name = "REDIRECT_PROTOCOLLO", length = 35)
    private String redirectProtocollo;

    @Column(name = "ID_SERV_PLUGIN", length = 35)
    private String idServPlugin;

    @Column(name = "ID_CLUSTER")
    private String idCluster;

    @Column(name = "ID_FESP_INSTANCE", length = 275)
    private String idFespInstance;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "LENTO", nullable = false)
    private Boolean lento = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "RT_PUSH", nullable = false)
    private Boolean rtPush = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "AGID_CHANNEL", nullable = false)
    private Boolean agidChannel = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ON_US", nullable = false)
    private Boolean onUs = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "CARRELLO_CARTE", nullable = false)
    private Boolean carrelloCarte = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "RECOVERY", nullable = false)
    private Boolean recovery = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "MARCA_BOLLO_DIGITALE", nullable = false)
    private Boolean marcaBolloDigitale = false;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "FLAG_IO")
    private Boolean flagIo;

    @Column(name = "VERSIONE_PRIMITIVE")
    private Integer versionePrimitive;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "FLAG_PSP_CP", nullable = false)
    private Boolean flagPspCp = false;
}
