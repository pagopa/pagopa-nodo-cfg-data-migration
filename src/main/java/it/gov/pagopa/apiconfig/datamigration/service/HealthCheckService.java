package it.gov.pagopa.apiconfig.datamigration.service;

import it.gov.pagopa.apiconfig.datamigration.repository.oracle.OracleDBSystemRepository;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.PostgresDBSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    @Autowired
    private OracleDBSystemRepository oracleDbRepo;

    @Autowired
    private PostgresDBSystemRepository postgresDbRepo;

    public boolean getHealthCheckForOracleDB() {
        try {
            return oracleDbRepo.healthCheck().isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }


    public boolean getHealthCheckForPostgresDB() {
        try {
            return postgresDbRepo.healthCheck().isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }
}
