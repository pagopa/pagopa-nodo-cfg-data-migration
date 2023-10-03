package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "CODIFICHE")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Codifiche {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long objId;

    @Column(name = "ID_CODIFICA", nullable = false, length = 35)
    private String idCodifica;

    @Column(name = "DESCRIZIONE", length = 35)
    private String descrizione;
}
