package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import it.gov.pagopa.apiconfig.starter.entity.IntermediariPa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediariPaDestRepository extends JpaRepository<IntermediariPa, Long> {
}
