package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.WfespPluginConf;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WfespPluginConfSrcRepository extends PagingAndSortingRepository<WfespPluginConf, Long> {
}
