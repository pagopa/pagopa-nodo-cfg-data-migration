package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.CodifichePa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodifichePaDestRepository extends JpaRepository<CodifichePa, Long> {

}
