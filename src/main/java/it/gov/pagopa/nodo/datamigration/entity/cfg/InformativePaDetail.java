package it.gov.pagopa.nodo.datamigration.entity.cfg;

import it.gov.pagopa.nodo.datamigration.util.YesNoConverter;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "INFORMATIVE_PA_DETAIL")
public class InformativePaDetail {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "FLAG_DISPONIBILITA", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean flagDisponibilita = false;

    @Column(name = "GIORNO", length = 35)
    private String giorno;

    @Column(name = "TIPO", length = 35)
    private String tipo;

    @Column(name = "FK_INFORMATIVA_PA_MASTER", nullable = false)
    private Long fkInformativaPaMaster;
}
