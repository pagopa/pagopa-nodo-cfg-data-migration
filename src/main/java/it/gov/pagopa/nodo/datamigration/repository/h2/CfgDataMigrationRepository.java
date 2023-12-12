package it.gov.pagopa.nodo.datamigration.repository.h2;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CfgDataMigrationRepository extends JpaRepository<DataMigration, String> {

    Optional<DataMigration> findById(String id);

    Optional<DataMigration> findTopByOrderByStartDesc();
}
