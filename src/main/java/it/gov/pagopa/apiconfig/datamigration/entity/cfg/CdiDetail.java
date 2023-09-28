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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CDI_MASTER", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CdiMaster fkCdiMaster;

    @Column(name = "CANALE_APP")
    private Long canaleApp;

    @Column(name = "TAGS", length = 135)
    private String tags;

    @Column(name = "LOGO_SERVIZIO")
    @ToString.Exclude
    private byte[] logoServizio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fkCdiDetail", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CdiInformazioniServizio> cdiInformazioniServizio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fkCdiDetail", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CdiFasciaCostoServizio> cdiFasciaCostoServizio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cdiDetail", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CdiPreference> cdiPreference;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PSP_CANALE_TIPO_VERSAMENTO", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PspCanaleTipoVersamento pspCanaleTipoVersamento;
}
