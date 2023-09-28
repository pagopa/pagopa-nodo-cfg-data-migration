package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PSP_CANALE_TIPO_VERSAMENTO")
@Setter
@Getter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PspCanaleTipoVersamento {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(
            name = "FK_CANALE_TIPO_VERSAMENTO",
            nullable = false,
            insertable = false,
            updatable = false)
    private Long fkCanaleTipoVersamento;

    @Column(name = "FK_PSP", nullable = false, insertable = false, updatable = false)
    private Long fkPsp;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CANALE_TIPO_VERSAMENTO", nullable = false)
    private CanaleTipoVersamento canaleTipoVersamento;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PSP", nullable = false)
    private Psp psp;
}
