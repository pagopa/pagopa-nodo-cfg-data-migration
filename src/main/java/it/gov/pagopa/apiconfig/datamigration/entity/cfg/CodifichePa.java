package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;

@Table(name = "CODIFICHE_PA")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CodifichePa {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "CODICE_PA", nullable = false, length = 35)
    private String codicePa;

    @Column(name = "FK_CODIFICA", nullable = false)
    private Long fkCodifica;

    @Column(name = "FK_PA", nullable = false)
    private Long fkPa;
}
