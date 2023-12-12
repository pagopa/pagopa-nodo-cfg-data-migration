package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanAttributesMaster;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IbanAttributesMasterSrcRepository extends PagingAndSortingRepository<IbanAttributesMaster, Long> {
}
