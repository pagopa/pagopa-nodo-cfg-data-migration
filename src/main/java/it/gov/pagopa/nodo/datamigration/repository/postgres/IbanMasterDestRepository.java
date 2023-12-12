package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanMasterDestRepository extends JpaRepository<IbanMaster, Long> {
}
