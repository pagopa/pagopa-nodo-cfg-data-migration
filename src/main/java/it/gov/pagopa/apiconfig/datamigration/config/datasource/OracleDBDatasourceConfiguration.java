package it.gov.pagopa.apiconfig.datamigration.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracledbTransactionManager",
        basePackages = { "it.gov.pagopa.apiconfig.datamigration.repository.oracle" }
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

    @Primary
    @Bean(name = "oracledbDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .username(username)
                .password(password)
                .url(jdbcUrl)
                .driverClassName(driverClassName)
                .build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    @ConditionalOnBean(name = "oracledbDataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean () {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("it.gov.pagopa.apiconfig.datamigration.entity");
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
