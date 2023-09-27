package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.Pa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaDestRepository extends JpaRepository<Pa, Long> {

}
