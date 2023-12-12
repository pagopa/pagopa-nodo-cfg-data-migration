package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.Iban;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanDestRepository extends JpaRepository<Iban, Long> {
}
