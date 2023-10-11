package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.WfespPluginConf;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WfespPluginConfSrcRepository extends PagingAndSortingRepository<WfespPluginConf, Long> {
}
