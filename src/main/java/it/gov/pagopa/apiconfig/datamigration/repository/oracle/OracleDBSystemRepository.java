package it.gov.pagopa.apiconfig.datamigration.repository.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

@Repository
public class OracleDBSystemRepository {

    @Autowired
    @Qualifier("oracleEntityManagerFactory")
    EntityManagerFactory emFactory;

    public Optional<Object> healthCheck() {
        return Optional.of(emFactory.createEntityManager().createNativeQuery("SELECT 1 FROM DUAL").getSingleResult());
    }

    public Long readHibernateSequence() {
        String newSequenceLastNumber = emFactory.createEntityManager()
                .createNativeQuery("SELECT last_number FROM all_sequences WHERE sequence_name = 'HIBERNATE_SEQUENCE';")
                .getSingleResult().toString();
        return Long.getLong(newSequenceLastNumber);
    }
}
