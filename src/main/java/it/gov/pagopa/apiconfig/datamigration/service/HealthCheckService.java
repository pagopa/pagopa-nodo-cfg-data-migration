package it.gov.pagopa.apiconfig.datamigration.service;

import it.gov.pagopa.apiconfig.datamigration.repository.oracle.OracleDBHealthCheckRepository;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.PostgresDBHealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private OracleDBHealthCheckRepository oracleDbRepo;

    @Autowired
    private PostgresDBHealthCheckRepository postgresDbRepo;

    public boolean getHealthCheckForOracleDB() {
        try {
            return postgresDbRepo.healthCheck().isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }


    public boolean getHealthCheckForPostgresDB() {
        try {
            return oracleDbRepo.healthCheck().isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }
}
