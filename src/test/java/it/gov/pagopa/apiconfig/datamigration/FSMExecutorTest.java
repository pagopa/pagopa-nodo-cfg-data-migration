package it.gov.pagopa.apiconfig.datamigration;

import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.apiconfig.datamigration.exception.AppException;
import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import it.gov.pagopa.apiconfig.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.CfgDataMigrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//TODO finish this, WIP
class FSMExecutorTest {

    @InjectMocks
    private FSMExecutor fsmExecutor;

    @Mock
    private CfgDataMigrationRepository cfgDataMigrationRepo;

    @Mock
    private Map<String, Step> steps;

    @Mock
    private Step stepMock;

    @Mock
    private DataMigration dataMigrationMock;

    @Mock
    private FSMSharedState fsmSharedStateMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(steps.get(anyString())).thenReturn(stepMock);
        when(fsmExecutor.getSharedState()).thenReturn(fsmSharedStateMock);
    }

    @Test
    void testStartWhenNotLocked() {
        when(fsmSharedStateMock.isInLock()).thenReturn(false);
        fsmExecutor.start();
        verify(steps).get(anyString());
        verify(stepMock).call();
    }

    @Test
    void testStartWhenLocked() {
        when(fsmSharedStateMock.isInLock()).thenReturn(true);
        assertThrows(AppException.class, () -> fsmExecutor.start());
        verify(steps, never()).get(anyString());
    }

    @Test
    void testRestartWhenLocked() {
        when(fsmSharedStateMock.isInLock()).thenReturn(true);
        assertThrows(AppException.class, () -> fsmExecutor.restart());
        verify(cfgDataMigrationRepo, never()).findTopByOrderByStartDesc();
    }

    @Test
    void testRestartWhenInProgress() {
        when(cfgDataMigrationRepo.findTopByOrderByStartDesc()).thenReturn(Optional.of(dataMigrationMock));
        when(dataMigrationMock.getStatus()).thenReturn(MigrationStepStatus.IN_PROGRESS.toString());
        assertThrows(AppException.class, () -> fsmExecutor.restart());
        verify(cfgDataMigrationRepo).findTopByOrderByStartDesc();
    }

    @Test
    void testForceStopWhenNotLocked() {
        when(fsmSharedStateMock.isInLock()).thenReturn(false);
        assertThrows(AppException.class, () -> fsmExecutor.forceStop());
    }

    @Test
    void testForceStopWhenLockedAndBlockNotRequested() {
        when(fsmSharedStateMock.isInLock()).thenReturn(true);
        when(fsmSharedStateMock.isBlockRequested()).thenReturn(false);
        fsmExecutor.forceStop();
        verify(fsmSharedStateMock).requestBlock();
    }

    @Test
    void testRestart() {
        when(cfgDataMigrationRepo.findTopByOrderByStartDesc()).thenReturn(Optional.of(dataMigrationMock));
        when(dataMigrationMock.getStatus()).thenReturn(MigrationStepStatus.COMPLETED.toString());
        fsmExecutor.restart();
        verify(cfgDataMigrationRepo).findTopByOrderByStartDesc();
        verify(cfgDataMigrationRepo).saveAndFlush(any());
    }
}