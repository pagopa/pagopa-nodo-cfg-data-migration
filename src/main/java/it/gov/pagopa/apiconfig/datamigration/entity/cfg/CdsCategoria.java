package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CDS_CATEGORIE")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CdsCategoria {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIZIONE")
    private String description;
}
