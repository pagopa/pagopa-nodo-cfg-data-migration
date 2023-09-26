package it.gov.pagopa.apiconfig.datamigration.repository;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CfgDataMigrationRepository extends JpaRepository<DataMigration, String> {
    @Query("SELECT dm FROM DataMigration dm ORDER BY dm.start DESC")
    Optional<DataMigration> readLastMigration();
}
