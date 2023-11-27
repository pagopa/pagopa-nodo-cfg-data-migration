package it.gov.pagopa.nodo.datamigration.repository.postgres;

import it.gov.pagopa.nodo.datamigration.entity.cfg.IcaBinaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IcaBinaryFileDestRepository extends JpaRepository<IcaBinaryFile, Long> {
}
