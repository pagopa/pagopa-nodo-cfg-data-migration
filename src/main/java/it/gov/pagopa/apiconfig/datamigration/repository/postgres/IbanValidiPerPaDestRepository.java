package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.IbanValidiPerPa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbanValidiPerPaDestRepository extends JpaRepository<IbanValidiPerPa, Long> {

}
