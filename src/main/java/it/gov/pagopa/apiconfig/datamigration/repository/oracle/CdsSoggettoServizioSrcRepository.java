package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.CdsSoggettoServizio;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdsSoggettoServizioSrcRepository extends PagingAndSortingRepository<CdsSoggettoServizio, Long> {
}
