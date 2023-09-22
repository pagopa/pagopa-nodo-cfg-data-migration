package it.gov.pagopa.apiconfig.datamigration.controller;

import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;

public class MigrationController {

    @Autowired
    @Qualifier("executor")
    private FSMExecutor fsmExecutor;


    @GetMapping("/start")
    public void start() throws Exception {
        // TODO make this call asyncronous!
        fsmExecutor.execute();
    }

    @GetMapping("/status")
    public void status() throws Exception {
        fsmExecutor.execute();
    }

    @GetMapping("/restart")
    public void restart() throws Exception {
        // TODO make this call asyncronous!
        fsmExecutor.execute();
    }

    @GetMapping("/stop")
    public void stop() {
        fsmExecutor.forceStop();
    }
}
