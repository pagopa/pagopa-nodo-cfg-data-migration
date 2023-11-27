package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanAttributes;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IbanAttributesSrcRepository extends PagingAndSortingRepository<IbanAttributes, Long> {
}
