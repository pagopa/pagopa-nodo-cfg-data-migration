package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IbanMaster;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IbanMasterSrcRepository extends PagingAndSortingRepository<IbanMaster, Long> {
}
