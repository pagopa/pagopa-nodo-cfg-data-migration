package it.gov.pagopa.nodo.datamigration.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracledbTransactionManager",
        basePackages = { "it.gov.pagopa.nodo.datamigration.repository.oracle" }
)
public class OracleDBDatasourceConfiguration {

    @Value("${persistence.oracledb.jdbc-url}")
    private String jdbcUrl;

    @Value("${persistence.oracledb.username}")
    private String username;

    @Value("${persistence.oracledb.password}")
    private String password;

    @Value("${persistence.oracledb.default_schema}")
    private String defaultSchema;

    @Value("${persistence.oracledb.driver-class-name}")
    private String driverClassName;

    @Value("${persistence.oracledb.hikari.connectionTimeout}")
    private String connectionTimeout;

    @Value("${persistence.oracledb.hikari.maxLifetime}")
    private String maxLifetime;

    @Value("${persistence.oracledb.hikari.keepaliveTime}")
    private String keepaliveTime;

    @Value("${persistence.oracledb.hikari.connection-test-query}")
    private String connectionTestQuery;

    @Primary
    @Bean(name = "oracledbDataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setConnectionTimeout(Long.getLong(connectionTimeout));
        hikariConfig.setMaxLifetime(Long.getLong(maxLifetime));
        hikariConfig.setKeepaliveTime(Long.getLong(keepaliveTime));
        hikariConfig.setConnectionTestQuery(connectionTestQuery);
        return new HikariDataSource(hikariConfig);
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    @ConditionalOnBean(name = "oracledbDataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean () {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("it.gov.pagopa.nodo.datamigration.entity");
        entityManager.setPersistenceUnitName("oracledbUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        props.put("hibernate.database-platform", "org.hibernate.dialect.Oracle12cDialect");
        props.put("hibernate.ddl-auto", "none");
        props.put("hibernate.hbm2ddl.auto", "none");
        props.put("hibernate.default_schema", defaultSchema);
        props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        entityManager.setJpaProperties(props);

        return entityManager;
    }

    @Primary
    @Bean(name = "oracledbTransactionManager")
    @ConditionalOnMissingBean(type = "JpaTransactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
