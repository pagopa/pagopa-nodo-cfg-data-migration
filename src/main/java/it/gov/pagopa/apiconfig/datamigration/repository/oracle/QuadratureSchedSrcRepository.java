package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadratureSchedSrcRepository extends PagingAndSortingRepository<Object, Long> {
}
