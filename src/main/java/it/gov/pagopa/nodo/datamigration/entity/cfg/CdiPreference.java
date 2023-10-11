package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

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

    @Column(name = "FK_INFORMATIVA_DETAIL", nullable = false)
    private Long fkInformativaDetail;

    @Column(name = "SELLER", nullable = false)
    private String seller;

    @Column(name = "BUYER")
    private String buyer;

    @Column(name = "COSTO_CONVENZIONE", nullable = false)
    private Double costoConvenzione;
}
