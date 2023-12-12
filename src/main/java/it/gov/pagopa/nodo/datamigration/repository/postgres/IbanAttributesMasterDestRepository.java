package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanAttributesMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanAttributesMasterDestRepository extends JpaRepository<IbanAttributesMaster, Long> {
}
