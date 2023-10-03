package it.gov.pagopa.nodo.datamigration.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlTransactionManager",
        basePackages = { "it.gov.pagopa.nodo.datamigration.repository.postgres" }
)
public class PostgreSQLDatasourceConfiguration {

    @Value("${persistence.postgresql.jdbc-url}")
    private String jdbcUrl;

    @Value("${persistence.postgresql.username}")
    private String username;

    @Value("${persistence.postgresql.password}")
    private String password;

    @Value("${persistence.postgresql.default_schema}")
    private String defaultSchema;

    @Value("${persistence.postgresql.driver-class-name}")
    private String driverClassName;

    @Value("${persistence.postgresql.hikari.connectionTimeout}")
    private String connectionTimeout;

    @Value("${persistence.postgresql.hikari.maxLifetime}")
    private String maxLifetime;

    @Value("${persistence.postgresql.hikari.keepaliveTime}")
    private String keepaliveTime;

    @Bean(name = "postgresqlDataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setConnectionTimeout(Long.parseLong(connectionTimeout));
        hikariConfig.setMaxLifetime(Long.parseLong(maxLifetime));
        hikariConfig.setKeepaliveTime(Long.parseLong(keepaliveTime));
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "postgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean (
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresqlDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManager = builder
                .dataSource(dataSource)
                .packages("it.gov.pagopa.nodo.datamigration.entity")
                .persistenceUnit("postgresqlUnit")
                .build();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);


        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.database-platform", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.ddl-auto", "none");
        props.put("hibernate.hbm2ddl.auto", "none");
        props.put("hibernate.default_schema", defaultSchema);
        props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        entityManager.setJpaProperties(props);

        return entityManager;
    }

    @Bean(name = "postgresqlTransactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

