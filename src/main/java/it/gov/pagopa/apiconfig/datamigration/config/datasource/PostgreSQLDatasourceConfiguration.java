package it.gov.pagopa.apiconfig.datamigration.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlTransactionManager",
        basePackages = { "it.gov.pagopa.apiconfig.datamigration.repository.postgres" }
)
public class PostgreSQLDatasourceConfiguration {

    private final PersistenceProperties properties;

    public PostgreSQLDatasourceConfiguration(PersistenceProperties properties) {
        this.properties = properties;
    }

    @Bean(name = "postgresqlDataSource")
    @ConfigurationProperties(prefix = "persistence.postgresql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean (
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresqlDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManager = builder
                .dataSource(dataSource)
                .packages("it.gov.pagopa.apiconfig.datamigration.entity")
                .persistenceUnit("postgresqlUnit")
                .build();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaPropertyMap(properties.getPostgresql().getHibernate().getProperties());
        return entityManager;
    }

    @Bean(name = "postgresqlTransactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

