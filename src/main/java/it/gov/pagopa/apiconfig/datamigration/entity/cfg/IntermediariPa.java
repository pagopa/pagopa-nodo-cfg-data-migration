package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.util.YesNoConverter;
import lombok.*;

import javax.persistence.*;

@Table(name = "INTERMEDIARI_PA")
@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IntermediariPa {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "ID_INTERMEDIARIO_PA", nullable = false, length = 35)
    private String idIntermediarioPa;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled = false;

    @Column(name = "CODICE_INTERMEDIARIO")
    private String codiceIntermediario;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "FAULT_BEAN_ESTESO", nullable = false)
    private Boolean faultBeanEsteso = false;
}
