package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IcaBinaryFile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IcaBinaryFileSrcRepository extends PagingAndSortingRepository<IcaBinaryFile, Long> {
}
