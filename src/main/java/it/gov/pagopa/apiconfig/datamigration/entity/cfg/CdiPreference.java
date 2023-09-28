package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CDI_PREFERENCES")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class CdiPreference {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_INFORMATIVA_DETAIL", nullable = false)
    @ToString.Exclude
    private CdiDetail cdiDetail;

    @Column(name = "SELLER", nullable = false)
    private String seller;

    @Column(name = "BUYER")
    private String buyer;

    @Column(name = "COSTO_CONVENZIONE", nullable = false)
    private Double costoConvenzione;
}