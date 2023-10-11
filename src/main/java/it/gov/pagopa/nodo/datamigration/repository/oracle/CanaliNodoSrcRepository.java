package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.CanaliNodo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanaliNodoSrcRepository extends PagingAndSortingRepository<CanaliNodo, Long> {
}
