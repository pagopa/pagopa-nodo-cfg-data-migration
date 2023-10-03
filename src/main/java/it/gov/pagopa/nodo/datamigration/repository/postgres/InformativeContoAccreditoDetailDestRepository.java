package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.InformativeContoAccreditoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativeContoAccreditoDetailDestRepository extends JpaRepository<InformativeContoAccreditoDetail, Long> {

}
