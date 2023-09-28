package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
public class PostgresDBHealthCheckRepository {

    @Autowired
    @Qualifier("postgresqlEntityManagerFactory")
    EntityManagerFactory emFactory;

    public Optional<Object> healthCheck() {
        return Optional.of(emFactory.createEntityManager().createNativeQuery("SELECT 1").getSingleResult());
    }
}
