package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.CdsSoggettoServizio;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CdsSoggettoServizioSrcRepository extends PagingAndSortingRepository<CdsSoggettoServizio, Long> {
}
