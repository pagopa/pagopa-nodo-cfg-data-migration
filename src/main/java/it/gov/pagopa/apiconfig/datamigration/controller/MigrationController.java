package it.gov.pagopa.apiconfig.datamigration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gov.pagopa.apiconfig.datamigration.model.migration.MigrationExecutionMessage;
import it.gov.pagopa.apiconfig.datamigration.model.migration.MigrationStatus;
import it.gov.pagopa.apiconfig.datamigration.service.MigrationService;
import it.gov.pagopa.apiconfig.datamigration.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "DB Migration", description = "Everything about DB Migration")
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @Operation(
            summary = "Start the migration",
            security = {
                    @SecurityRequirement(name = "ApiKey")
            },
            tags = {"DB Migration"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema()))
    })
    @PostMapping("/start")
    public ResponseEntity<MigrationExecutionMessage> start() {
        migrationService.startMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_START_OK)
                        .build()
        );
    }

    @Operation(
            summary = "Get the status of the migration",
            security = {
                    @SecurityRequirement(name = "ApiKey")
            },
            tags = {"DB Migration"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Service unavailable", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MigrationExecutionMessage.class)))
    })
    @GetMapping("/status")
    public ResponseEntity<MigrationStatus> status() {
        return ResponseEntity.status(200).body(migrationService.getMigrationStatus());
    }

    @Operation(
            summary = "Start again the migration, if interrupted",
            security = {
                    @SecurityRequirement(name = "ApiKey")
            },
            tags = {"DB Migration"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema()))
    })
    @PostMapping("/restart")
    public ResponseEntity<MigrationExecutionMessage> restart() {
        migrationService.reStartMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_RESTART_OK)
                        .build()
        );
    }

    @Operation(
            summary = "Stop the migration",
            security = {
                    @SecurityRequirement(name = "ApiKey")
            },
            tags = {"DB Migration"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MigrationExecutionMessage.class))),
            @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema()))
    })
    @PostMapping("/stop")
    public ResponseEntity<MigrationExecutionMessage> stop() {
        migrationService.forcedStopMigration();
        return ResponseEntity.ok(
                MigrationExecutionMessage.builder()
                        .message(Constants.API_STOP_OK)
                        .build()
        );
    }
}
