package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(
        name = "CANALE_TIPO_VERSAMENTO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"FK_CANALE", "FK_TIPO_VERSAMENTO"})})
@Builder

public class CanaleTipoVersamento {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "FK_CANALE", nullable = false, insertable = false, updatable = false)
    private Long fkCanale;

    @Column(name = "FK_TIPO_VERSAMENTO", nullable = false, insertable = false, updatable = false)
    private Long fkTipoVersamento;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CANALE", nullable = false)
    private Canali canale;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_TIPO_VERSAMENTO", nullable = false)
    private TipiVersamento tipoVersamento;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "fkCanaleTipoVersamento",
            cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<PspCanaleTipoVersamento> pspCanaleTipoVersamentoList;
}