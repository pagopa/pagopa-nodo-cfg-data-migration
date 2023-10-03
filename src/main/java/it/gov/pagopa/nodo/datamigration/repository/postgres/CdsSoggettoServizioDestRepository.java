package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.CdsSoggettoServizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdsSoggettoServizioDestRepository extends JpaRepository<CdsSoggettoServizio, Long> {

}
