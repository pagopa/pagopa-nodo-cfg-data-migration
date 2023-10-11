package it.gov.pagopa.nodo.datamigration.repository.oracle;

import it.gov.pagopa.nodo.datamigration.entity.cfg.PspCanaleTipoVersamento;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PspCanaleTipoVersamentoSrcRepository extends PagingAndSortingRepository<PspCanaleTipoVersamento, Long> {
}
