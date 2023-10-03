package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "CDS_SOGGETTO")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CdsSoggetto {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_DOMINIO", nullable = false)
    private String creditorInstitutionCode;

    @Column(name = "DESCRIZIONE_ENTE")
    private String creditorInstitutionDescription;
}
