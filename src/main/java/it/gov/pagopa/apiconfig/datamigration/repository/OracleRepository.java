package it.gov.pagopa.apiconfig.datamigration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OracleRepository extends JpaRepository<Table, String> {
}
