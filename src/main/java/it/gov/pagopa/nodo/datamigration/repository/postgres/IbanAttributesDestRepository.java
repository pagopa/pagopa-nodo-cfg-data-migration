package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanAttributesDestRepository extends JpaRepository<IbanAttributes, Long> {
}
