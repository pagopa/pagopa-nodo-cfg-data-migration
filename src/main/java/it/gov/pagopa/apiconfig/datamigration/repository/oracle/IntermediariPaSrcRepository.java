package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.starter.entity.IntermediariPa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediariPaSrcRepository extends PagingAndSortingRepository<IntermediariPa, Long> {
}
