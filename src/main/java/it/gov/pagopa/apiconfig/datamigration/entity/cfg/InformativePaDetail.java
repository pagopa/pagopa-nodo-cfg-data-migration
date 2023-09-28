package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.starter.util.YesNoConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_INFORMATIVA_PA_MASTER", nullable = false)
    @ToString.Exclude
    private InformativePaMaster fkInformativaPaMaster;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "fkInformativaPaDetail",
            cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<InformativePaFasce> fasce;
}