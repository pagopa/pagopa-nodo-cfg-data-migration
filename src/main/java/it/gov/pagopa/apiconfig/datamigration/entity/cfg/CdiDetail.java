package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "CDI_DETAIL")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CdiDetail {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "NOME_SERVIZIO")
    private String nomeServizio;

    @Column(name = "PRIORITA", nullable = false)
    private Long priorita;

    @Column(name = "MODELLO_PAGAMENTO", nullable = false)
    private Long modelloPagamento;

    @Column(name = "FK_CDI_MASTER", nullable = false)
    private Long fkCdiMaster;

    @Column(name = "FK_PSP_CANALE_TIPO_VERSAMENTO", nullable = false)
    private Long pspCanaleTipoVersamento;

    @Column(name = "TAGS", length = 135)
    private String tags;

    @Column(name = "LOGO_SERVIZIO")
    @ToString.Exclude
    private byte[] logoServizio;
}
