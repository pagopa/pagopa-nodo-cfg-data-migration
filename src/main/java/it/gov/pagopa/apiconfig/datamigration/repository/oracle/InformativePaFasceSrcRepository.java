package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.InformativePaFasce;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativePaFasceSrcRepository extends PagingAndSortingRepository<InformativePaFasce, Long> {
}
