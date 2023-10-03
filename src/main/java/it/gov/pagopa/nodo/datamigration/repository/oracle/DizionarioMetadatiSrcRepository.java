package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.DizionarioMetadati;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DizionarioMetadatiSrcRepository extends PagingAndSortingRepository<DizionarioMetadati, Long> {
}
