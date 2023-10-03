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
@Table(name = "PDD")
@Builder(toBuilder = true)
public class Pdd {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_PDD", nullable = false, length = 35)
    private String idPdd;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

    @Column(name = "descrizione", nullable = false, length = 35)
    private String descrizione;

    @Column(name = "IP", nullable = false, length = 15)
    private String ip;

    @Column(name = "porta")
    private Integer porta;
}
