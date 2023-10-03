package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "CANALE_TIPO_VERSAMENTO", uniqueConstraints = {@UniqueConstraint(columnNames = {"FK_CANALE", "FK_TIPO_VERSAMENTO"})})
@Builder
public class CanaleTipoVersamento {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "FK_CANALE", nullable = false)
    private Long fkCanale;

    @Column(name = "FK_TIPO_VERSAMENTO", nullable = false)
    private Long fkTipoVersamento;
}
