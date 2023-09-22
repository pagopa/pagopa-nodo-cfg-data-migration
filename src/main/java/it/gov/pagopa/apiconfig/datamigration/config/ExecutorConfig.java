package it.gov.pagopa.apiconfig.datamigration.config;

import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ExecutorConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public FSMExecutor executor() {
        return new FSMExecutor();
    }


}
