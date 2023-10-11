package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.InformativePaDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativePaDetailDestRepository extends JpaRepository<InformativePaDetail, Long> {

}
