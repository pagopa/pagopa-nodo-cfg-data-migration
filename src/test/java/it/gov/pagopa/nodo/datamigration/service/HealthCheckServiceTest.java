package it.gov.pagopa.nodo.datamigration.service;

import it.gov.pagopa.nodo.datamigration.repository.oracle.OracleDBSystemRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.PostgresDBSystemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = HealthCheckService.class)
class HealthCheckServiceTest {

    @MockBean
    private OracleDBSystemRepository oracleDbRepo;

    @MockBean
    private PostgresDBSystemRepository postgresDbRepo;

    @InjectMocks
    private HealthCheckService healthCheckService;

    @Test
    public void testHealthCheckForOracleDBSuccess() {
        Mockito.when(oracleDbRepo.healthCheck()).thenReturn(java.util.Optional.of("OK"));

        boolean result = healthCheckService.getHealthCheckForOracleDB();

        assertTrue(result);
    }

    @Test
    public void testHealthCheckForOracleDBFailure() {
        Mockito.when(oracleDbRepo.healthCheck()).thenThrow(new DataAccessException("Connection Error") {});

        boolean result = healthCheckService.getHealthCheckForOracleDB();

        assertFalse(result);
    }

    @Test
    public void testHealthCheckForPostgresDBSuccess() {
        Mockito.when(postgresDbRepo.healthCheck()).thenReturn(java.util.Optional.of("OK"));

        boolean result = healthCheckService.getHealthCheckForPostgresDB();

        assertTrue(result);
    }

    @Test
    public void testHealthCheckForPostgresDBFailure() {
        Mockito.when(postgresDbRepo.healthCheck()).thenThrow(new DataAccessException("Connection Error") {});

        boolean result = healthCheckService.getHealthCheckForPostgresDB();

        assertFalse(result);
    }
}
