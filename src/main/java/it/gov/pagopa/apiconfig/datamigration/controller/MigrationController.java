package it.gov.pagopa.apiconfig.datamigration.controller;

import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {

    @Autowired
    @Qualifier("executor")
    private FSMExecutor fsmExecutor;


    @GetMapping("/start")
    public void start() throws Exception {
        // TODO make this call asyncronous!
        fsmExecutor.start();
    }

    @GetMapping("/status")
    public void status() throws Exception {
        //fsmExecutor.execute();
    }

    @GetMapping("/restart")
    public void restart() throws Exception {
        // TODO make this call asyncronous!
        fsmExecutor.restart();
    }

    @GetMapping("/stop")
    public void stop() {
        fsmExecutor.forceStop();
    }
}
