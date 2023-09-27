package it.gov.pagopa.apiconfig.datamigration.service;

import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {
    @Autowired
    private FSMExecutor fsmExecutor;

    @Async
    public void startMigration() throws Exception {
            fsmExecutor.start();
        }

    @Async
    public void reStartMigration() throws Exception {
            fsmExecutor.restart();
        }

        public void forcedStopMigration() {
        fsmExecutor.forceStop();
        }
}
