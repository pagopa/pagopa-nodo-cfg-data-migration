package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CfgDataMigrationRepository extends JpaRepository<DataMigration, String> {

    Optional<DataMigration> findById(String id);

    Optional<DataMigration> findTopByOrderByStartDesc();
}
