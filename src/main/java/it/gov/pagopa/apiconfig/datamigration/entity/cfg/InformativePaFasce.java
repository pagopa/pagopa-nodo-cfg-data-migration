package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "INFORMATIVE_PA_FASCE")
public class InformativePaFasce {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ORA_A", length = 35)
    private String oraA;

    @Column(name = "ORA_DA", length = 35)
    private String oraDa;

    @Column(name = "FK_INFORMATIVA_PA_DETAIL", nullable = false)
    private Long fkInformativaPaDetail;
}
