package it.gov.pagopa.apiconfig.datamigration.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracledbTransactionManager",
        basePackages = { "it.gov.pagopa.apiconfig.datamigration.repository.oracle"}
)
public class OracleDBDatasourceConfiguration {

    private final PersistenceProperties properties;

    public OracleDBDatasourceConfiguration(PersistenceProperties properties) {
        this.properties = properties;
    }

    @Primary
    @Bean(name = "oracledbDataSource")
    @ConfigurationProperties(prefix = "persistence.oracledb")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean (
            EntityManagerFactoryBuilder builder,
            @Qualifier("oracledbDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManager = builder
                .dataSource(dataSource)
                .packages("it.gov.pagopa.apiconfig.datamigration.entity")
                .persistenceUnit("oracledbUnit")
                .build();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaPropertyMap(properties.getOracledb().getHibernate().getProperties());
        return entityManager;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager (
            @Qualifier("oracleEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
