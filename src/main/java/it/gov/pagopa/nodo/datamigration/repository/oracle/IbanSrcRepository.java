package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.Iban;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IbanSrcRepository extends PagingAndSortingRepository<Iban, Long> {
}
