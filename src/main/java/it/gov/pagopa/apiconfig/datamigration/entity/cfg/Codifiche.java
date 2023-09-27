package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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