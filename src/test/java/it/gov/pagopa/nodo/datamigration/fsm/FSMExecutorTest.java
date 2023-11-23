package it.gov.pagopa.nodo.datamigration.fsm;

import it.gov.pagopa.nodo.datamigration.entity.DataMigration;
import it.gov.pagopa.nodo.datamigration.enumeration.MigrationStepStatus;
import it.gov.pagopa.nodo.datamigration.enumeration.StepName;
import it.gov.pagopa.nodo.datamigration.exception.AppError;
import it.gov.pagopa.nodo.datamigration.exception.AppException;
import it.gov.pagopa.nodo.datamigration.repository.h2.CfgDataMigrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = FSMExecutor.class)
class FSMExecutorTest {

    @InjectMocks
    private FSMExecutor fsmExecutor;

    @MockBean
    private CfgDataMigrationRepository cfgDataMigrationRepo;

    @MockBean
    private Map<String, Step> steps;

    @Mock
    private Step step;

    @Mock
    private DataMigration dataMigration;

    @Mock
    private FSMSharedState fsmSharedState;

    @BeforeEach
    void setUp() {
        when(steps.get(anyString())).thenReturn(step);
        when(cfgDataMigrationRepo.findTopByOrderByStartDesc()).thenReturn(Optional.of(dataMigration));
    }

    @Test
    void testStart() {
        doAnswer(invocation -> {
            fsmSharedState.lock();
            return null;
        }).when(step).call();

        fsmExecutor.start();

        verify(fsmSharedState, times(1)).lock();
    }

    @Test
    void testStartAppException() {
        fsmSharedState.lock();

        fsmExecutor.start();

        AppException exception = assertThrows(AppException.class, () -> fsmExecutor.start());
        assertEquals(AppError.STATUS_ALREADY_LOCKED.getDetails(), exception.getMessage());
    }

    @Test
    void testRestart() {
        when(dataMigration.getStatus()).thenReturn(MigrationStepStatus.BLOCKED.toString());
        when(dataMigration.getLastExecutedStep()).thenReturn(StepName.EXECUTE_BINARY_FILE_TABLE_MIGRATION.toString());
        fsmSharedState.unlock();

        fsmExecutor.restart();
    }

    @Test
    void testRestartAlreadyLocked() {
        when(fsmSharedState.isInLock()).thenReturn(true);
        setField(fsmExecutor, "sharedState", fsmSharedState);

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.restart()
        );

        assertEquals(AppError.STATUS_ALREADY_LOCKED.getDetails(), thrown.getMessage());
    }

    @Test
    void testRestartMigrationInProgress() {
        when(dataMigration.getStatus()).thenReturn(MigrationStepStatus.IN_PROGRESS.toString());
        when(dataMigration.getLastExecutedStep()).thenReturn(StepName.EXECUTE_BINARY_FILE_TABLE_MIGRATION.toString());

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.restart()
        );

        assertEquals(AppError.MIGRATION_ALREADY_IN_PROGRESS.getDetails(), thrown.getMessage());
    }

    @Test
    void testRestartMigrationCompleted() {
        when(dataMigration.getStatus()).thenReturn(MigrationStepStatus.COMPLETED.toString());
        when(dataMigration.getLastExecutedStep()).thenReturn(StepName.END.toString());

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.restart()
        );
        assertEquals(AppError.MIGRATION_ALREADY_COMPLETED.getDetails(), thrown.getMessage());
    }

    @Test
    void testRestartMigrationNotRestartable() {
        when(dataMigration.getStatus()).thenReturn(MigrationStepStatus.TODO.toString());
        when(dataMigration.getLastExecutedStep()).thenReturn(StepName.START.toString());

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.restart()
        );
        assertEquals(AppError.MIGRATION_NOT_RESTARTABLE.getDetails(), thrown.getMessage());
    }

    @Test
    void testForceStop() {
        when(fsmSharedState.isBlockRequested()).thenReturn(false);
        when(fsmSharedState.isInLock()).thenReturn(true);
        setField(fsmExecutor, "sharedState", fsmSharedState);

        fsmExecutor.forceStop();

        verify(fsmSharedState, times(1)).requestBlock();
    }

    @Test
    void testForceStopBlockRequested() {
        when(fsmSharedState.isBlockRequested()).thenReturn(true);
        fsmSharedState.lock();
        setField(fsmExecutor, "sharedState", fsmSharedState);

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.forceStop()
        );

        assertEquals(AppError.FORCE_STOP_ALREADY_REQUESTED.getDetails(), thrown.getMessage());
    }

    @Test
    void testForceStopIsNotInLock() {
        fsmSharedState.unlock();

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.forceStop()
        );

        assertEquals(AppError.STATUS_NOT_LOCKED.getDetails(), thrown.getMessage());
    }

    @Test
    void getLastMigrationStatusAppException() {
        when(cfgDataMigrationRepo.findTopByOrderByStartDesc()).thenReturn(Optional.empty());

        AppException thrown = assertThrows(
                AppException.class,
                () -> fsmExecutor.getLastMigrationStatus()
        );

        assertEquals(AppError.NOT_FOUND_NO_VALID_MIGRATION_STATUS.getDetails(), thrown.getMessage());
    }
}
