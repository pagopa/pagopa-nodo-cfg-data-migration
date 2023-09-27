package it.gov.pagopa.apiconfig.datamigration.controller;

import it.gov.pagopa.apiconfig.datamigration.model.MigrationExecutionMessage;
import it.gov.pagopa.apiconfig.datamigration.model.MigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.service.MigrationService;
import it.gov.pagopa.apiconfig.datamigration.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @GetMapping("/start")
    public ResponseEntity<MigrationExecutionMessage> start() throws Exception {
        migrationService.startMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_START_OK)
                        .build()
        );
    }

    @GetMapping("/status")
    public ResponseEntity<MigrationStatus> status() throws Exception {
        //migrationService.getMigrationStatus();
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/restart")
    public ResponseEntity<MigrationExecutionMessage> restart() throws Exception {
        migrationService.reStartMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_RESTART_OK)
                        .build()
        );
    }

    @GetMapping("/stop")
    public ResponseEntity<MigrationExecutionMessage> stop() {
        migrationService.forcedStopMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_STOP_OK)
                        .build()
        );
    }
}
