package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.CdiFasciaCostoServizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdiFasciaCostoServizioDestRepository extends JpaRepository<CdiFasciaCostoServizio, Long> {

}
