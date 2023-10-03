package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "DIZIONARIO_METADATI")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DizionarioMetadati {

    @Id
    @Column(name = "CHIAVE", nullable = false)
    private String key;

    @Column(name = "DESCRIZIONE", nullable = false)
    private String description;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private ZonedDateTime startDate;

    @Column(name = "DATA_FINE_VALIDITA")
    private ZonedDateTime endDate;
}
