package it.gov.pagopa.nodo.datamigration.entity.cfg;

import it.gov.pagopa.nodo.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "CANALI")
@Builder(toBuilder = true)
public class Canali {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_CANALE", nullable = false, length = 35)
    private String idCanale;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled = false;

    @Column(name = "IP", length = 100)
    private String ip;

    @ToString.Exclude
    @Column(name = "PASSWORD", length = 15)
    private String password;

    @Column(name = "PORTA", nullable = false)
    private Long porta;

    @Column(name = "PROTOCOLLO", nullable = false)
    private String protocollo;

    @Column(name = "SERVIZIO", length = 100)
    private String servizio;

    @Column(name = "DESCRIZIONE", length = 70)
    private String descrizione;

    @Column(name = "FK_INTERMEDIARIO_PSP", nullable = false)
    private Long fkIntermediarioPsp;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "PROXY_ENABLED", nullable = false)
    private Boolean proxyEnabled = false;

    @Column(name = "PROXY_HOST", length = 100)
    private String proxyHost;

    @ToString.Exclude
    @Column(name = "PROXY_PASSWORD", length = 15)
    @EqualsAndHashCode.Exclude
    private String proxyPassword;

    @Column(name = "PROXY_PORT")
    private Long proxyPort;

    @Column(name = "PROXY_USERNAME", length = 15)
    private String proxyUsername;

    @Column(name = "TARGET_HOST")
    private String targetHost;

    @Column(name = "TARGET_PORT")
    private Long targetPort;

    @Column(name = "TARGET_PATH")
    private String targetPath;

    @Column(name = "FK_CANALI_NODO")
    private Long fkCanaliNodo;

    @Column(name = "TIMEOUT", nullable = false)
    private Long timeout;

    @Column(name = "NUM_THREAD", nullable = false)
    private Long numThread;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "USE_NEW_FAULT_CODE", nullable = false)
    private Boolean useNewFaultCode = false;

    @Column(name = "TIMEOUT_A", nullable = false)
    private Long timeoutA;

    @Column(name = "TIMEOUT_B", nullable = false)
    private Long timeoutB;

    @Column(name = "TIMEOUT_C", nullable = false)
    private Long timeoutC;

    @Column(name = "SERVIZIO_NMP")
    private String servizioNmp;

    @Column(name = "TARGET_HOST_NMP")
    private String targetHostNmp;

    @Column(name = "TARGET_PORT_NMP")
    private Long targetPortNmp;

    @Column(name = "TARGET_PATH_NMP")
    private String targetPathNmp;
}
