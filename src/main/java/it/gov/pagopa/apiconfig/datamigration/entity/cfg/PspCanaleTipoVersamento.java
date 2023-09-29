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

    @Column(name = "FK_CANALE_TIPO_VERSAMENTO", nullable = false)
    private Long fkCanaleTipoVersamento;

    @Column(name = "FK_PSP", nullable = false)
    private Long fkPsp;
}
