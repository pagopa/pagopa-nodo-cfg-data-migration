package it.gov.pagopa.nodo.datamigration.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
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

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2EntityManagerFactory",
        transactionManagerRef = "h2TransactionManager",
        basePackages = { "it.gov.pagopa.nodo.datamigration.repository.h2" }
)
public class H2DatasourceConfiguration {

    @Value("${persistence.h2.jdbc-url}")
    private String jdbcUrl;

    @Value("${persistence.h2.username}")
    private String username;

    @Value("${persistence.h2.password}")
    private String password;

//    @Value("${persistence.h2.default_schema}")
//    private String defaultSchema;

    @Value("${persistence.h2.driver-class-name}")
    private String driverClassName;

    @Value("${persistence.h2.hikari.connectionTimeout}")
    private String connectionTimeout;

    @Value("${persistence.h2.hikari.maxLifetime}")
    private String maxLifetime;

    @Value("${persistence.h2.hikari.keepaliveTime}")
    private String keepaliveTime;

    @Value("${persistence.h2.jdbc.batch.size}")
    private Integer jdbcBatchSize;

    @Bean(name = "h2DataSource")
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

    @Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean (
            EntityManagerFactoryBuilder builder,
            @Qualifier("h2DataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManager = builder
                .dataSource(dataSource)
                .packages("it.gov.pagopa.nodo.datamigration.entity")
                .persistenceUnit("h2Unit")
                .build();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);


        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.database-platform", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.jdbc.batch_size", jdbcBatchSize);
        props.put("hibernate.ddl-auto", "none");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        entityManager.setJpaProperties(props);

        return entityManager;
    }

    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

