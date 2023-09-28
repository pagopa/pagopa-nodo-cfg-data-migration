package it.gov.pagopa.apiconfig.datamigration.repository.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
public class PostgresDBSystemRepository {

    @Autowired
    @Qualifier("postgresqlEntityManagerFactory")
    EntityManagerFactory emFactory;

    public Optional<Object> healthCheck() {
        return Optional.of(emFactory.createEntityManager().createNativeQuery("SELECT 1").getSingleResult());
    }

    public Long updateHibernateSequence(Long lastValue) {
        String newSequenceLastNumber =  emFactory.createEntityManager()
                .createNativeQuery(String.format("SELECT setval('sequence_value', %d, FALSE);", lastValue))
                .getSingleResult().toString();
        return Long.getLong(newSequenceLastNumber);
    }
}
