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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_CODIFICA", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Codifiche fkCodifica;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PA", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pa fkPa;
}