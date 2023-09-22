package it.gov.pagopa.apiconfig.datamigration.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PostgresRepositoryImpl {
    private final DataSource dataSource;

    @Autowired
    public PostgresRepositoryImpl(@Qualifier("postgresDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
