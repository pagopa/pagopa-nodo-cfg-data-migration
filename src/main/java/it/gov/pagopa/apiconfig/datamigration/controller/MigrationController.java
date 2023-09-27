package it.gov.pagopa.apiconfig.datamigration.controller;

import it.gov.pagopa.apiconfig.datamigration.service.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @GetMapping("/start")
    public void start() throws Exception {
        migrationService.startMigration();
    }

    @GetMapping("/status")
    public void status() throws Exception {
        //migrationService.getMigrationStatus();
    }

    @GetMapping("/restart")
    public void restart() throws Exception {
        migrationService.reStartMigration();
    }

    @GetMapping("/stop")
    public void stop() {
        migrationService.forcedStopMigration();
    }
}
