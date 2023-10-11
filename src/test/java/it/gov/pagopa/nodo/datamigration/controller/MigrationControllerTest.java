package it.gov.pagopa.nodo.datamigration.controller;

import it.gov.pagopa.nodo.datamigration.model.migration.MigrationStatus;
import it.gov.pagopa.nodo.datamigration.service.MigrationService;
import it.gov.pagopa.nodo.datamigration.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MigrationController.class)
class MigrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MigrationService migrationService;

    @Test
    public void testStartMigration() throws Exception {
        doNothing().when(migrationService).startMigration();

        mockMvc.perform(MockMvcRequestBuilders.post("/start"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Constants.API_START_OK));
    }

    @Test
    public void testGetMigrationStatus() throws Exception {
        MigrationStatus status = new MigrationStatus();

        when(migrationService.getMigrationStatus()).thenReturn(status);

        mockMvc.perform(MockMvcRequestBuilders.get("/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRestartMigration() throws Exception {
        doNothing().when(migrationService).reStartMigration();

        mockMvc.perform(MockMvcRequestBuilders.post("/restart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Constants.API_RESTART_OK));
    }

    @Test
    public void testStopMigration() throws Exception {
        doNothing().when(migrationService).forcedStopMigration();

        mockMvc.perform(MockMvcRequestBuilders.post("/stop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Constants.API_STOP_OK));
    }
}
