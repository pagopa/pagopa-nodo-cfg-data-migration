/*import it.gov.pagopa.apiconfig.datamigration.entity.DataMigration;
import it.gov.pagopa.apiconfig.datamigration.enumeration.StepName;
import it.gov.pagopa.apiconfig.datamigration.exception.AppError;
import it.gov.pagopa.apiconfig.datamigration.exception.AppException;
import it.gov.pagopa.apiconfig.datamigration.fsm.FSMExecutor;
import it.gov.pagopa.apiconfig.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.apiconfig.datamigration.fsm.Step;
import it.gov.pagopa.apiconfig.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.apiconfig.datamigration.util.CommonUtils;
import it.gov.pagopa.apiconfig.datamigration.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FSMExecutorTest {

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

// Add more test cases as needed to cover other scenarios
}
 */