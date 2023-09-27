package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.InformativeContoAccreditoMaster;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativeContoAccreditoMasterSrcRepository extends PagingAndSortingRepository<InformativeContoAccreditoMaster, Long> {
}
