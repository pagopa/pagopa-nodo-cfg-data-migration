package it.gov.pagopa.nodo.datamigration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApplicationTest {

    @MockBean
    @Qualifier("oracledbDataSource")
    private DataSource oracledbDataSource;

    @MockBean
    @Qualifier("postgresqlDataSource")
    private DataSource postgresqlDataSource;

    @Test
    void contextLoads() {
        // check only if the context is loaded
        assertTrue(true);
    }
}

