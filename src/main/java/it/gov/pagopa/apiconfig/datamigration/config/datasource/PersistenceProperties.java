package it.gov.pagopa.apiconfig.datamigration.config.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "persistence")
public class PersistenceProperties {

    private OracleDBPersistenceProperties oracledb = new OracleDBPersistenceProperties();

    private PostgreSQLPersistenceProperties postgresql = new PostgreSQLPersistenceProperties();

    @Data
    public static class OracleDBPersistenceProperties {
        private HibernateProperties hibernate = new HibernateProperties();
    }

    @Data
    public static class PostgreSQLPersistenceProperties {
        private HibernateProperties hibernate = new HibernateProperties();
    }

    @Data
    public static class HibernateProperties {
        private Map<String, String> properties = new HashMap<>();
    }
}
