package it.gov.pagopa.nodo.datamigration.fsm;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


public class FSMExecutorTest {
/* 
    private FSMExecutor fsmExecutor;

    @Mock
    private CfgDataMigrationRepository cfgDataMigrationRepository;

    @Mock
    private Map<String, Step> steps;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fsmExecutor = new FSMExecutor();
        fsmExecutor.setCfgDataMigrationRepo(cfgDataMigrationRepository);
        fsmExecutor.setSteps(steps);
    }

    @Test
    public void testStartWhenStatusIsAlreadyLocked() {
        FSMSharedState sharedState = new FSMSharedState();
        sharedState.lock();
        fsmExecutor.setSharedState(sharedState);

        assertThrows(AppException.class, () -> {
            fsmExecutor.start(StepName.START);
        });
    }

    @Test
    public void testStart() {
        FSMSharedState sharedState = new FSMSharedState();
        fsmExecutor.setSharedState(sharedState);

        when(cfgDataMigrationRepository.findTopByOrderByStartDesc()).thenReturn(java.util.Optional.of(new DataMigration()));
        when(steps.get(any())).thenReturn(Mockito.mock(Step.class));
        when(steps.get(StepName.START.toString()).call()).thenReturn(StepName.NEXT_STEP);

        fsmExecutor.start(StepName.START);

        // Add assertions here to verify the behavior of your code 
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
 */
}
