package it.gov.pagopa.nodo.datamigration.repository.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.Optional;

@Repository
public class PostgresDBSystemRepository {

    @Autowired
    @Qualifier("postgresqlEntityManagerFactory")
    EntityManagerFactory emFactory;

    public Optional<Object> healthCheck() {
        return Optional.of(emFactory.createEntityManager().createNativeQuery("SELECT 1").getSingleResult());
    }

    public void updateHibernateSequence(Long lastValue) {
        EntityManager entityManager = emFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createNativeQuery(String.format("ALTER SEQUENCE hibernate_sequence RESTART WITH %d", lastValue)).executeUpdate();
        entityManager.flush();
        transaction.commit();
    }
}
