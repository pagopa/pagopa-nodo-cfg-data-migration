package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.InformativeContoAccreditoDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativeContoAccreditoDetailSrcRepository extends PagingAndSortingRepository<InformativeContoAccreditoDetail, Long> {
}
