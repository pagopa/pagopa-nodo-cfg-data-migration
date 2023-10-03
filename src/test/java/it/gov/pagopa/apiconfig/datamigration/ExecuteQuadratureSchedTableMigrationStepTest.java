package it.gov.pagopa.nodo.datamigration;

import it.gov.pagopa.nodo.datamigration.entity.cfg.QuadratureSched;
import it.gov.pagopa.nodo.datamigration.exception.migration.MigrationErrorOnStepException;
import it.gov.pagopa.nodo.datamigration.fsm.FSMSharedState;
import it.gov.pagopa.nodo.datamigration.fsm.step.ExecuteQuadratureSchedTableMigrationStep;
import it.gov.pagopa.nodo.datamigration.repository.oracle.QuadratureSchedSrcRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.CfgDataMigrationRepository;
import it.gov.pagopa.nodo.datamigration.repository.postgres.QuadratureSchedDestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/*
class ExecuteQuadratureSchedTableMigrationStepTest {

    @InjectMocks
    private ExecuteQuadratureSchedTableMigrationStep quadratureSchedTableMigrationStep;

    @Mock
    private CfgDataMigrationRepository cfgDataMigrationRepository;

    @Mock
    private FSMSharedState sharedState;

    @Mock
    private QuadratureSchedSrcRepository srcRepo;

    @Mock
    private QuadratureSchedDestRepository destRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sharedState.getDataMigrationStateId()).thenReturn("mock");
        quadratureSchedTableMigrationStep.attachSharedState(sharedState, cfgDataMigrationRepository);
    }

    @Test
    void testExecuteStep() throws Exception {
        QuadratureSched quadratureSched = new QuadratureSched();
        List<QuadratureSched> quadratureSchedList = Collections.singletonList(quadratureSched);
        Page<QuadratureSched> firstPage = new PageImpl<>(quadratureSchedList, PageRequest.of(0, 1), 2);
        Page<QuadratureSched> secondPage = new PageImpl<>(quadratureSchedList);
        when(srcRepo.findAll(any(Pageable.class)))
                .thenReturn(firstPage)
                .thenReturn(secondPage);

        quadratureSchedTableMigrationStep.executeStep();

        verify(srcRepo, times(2)).findAll(any(Pageable.class));
        verify(destRepo, times(2)).saveAllAndFlush(eq(quadratureSchedList));
    }

    @Test
    public void testExecuteStepThrowsException() {
        when(srcRepo.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        assertThrows(MigrationErrorOnStepException.class, () -> quadratureSchedTableMigrationStep.executeStep());
    }

    @Test
    public void testExecuteStepWithSaveFailure() {
        QuadratureSched quadratureSched = new QuadratureSched();
        List<QuadratureSched> quadratureSchedList = Collections.singletonList(quadratureSched);
        Page<QuadratureSched> pagedResponse = new PageImpl<>(quadratureSchedList);
        when(srcRepo.findAll(any(Pageable.class))).thenReturn(pagedResponse);
        doThrow(new RuntimeException()).when(destRepo).saveAllAndFlush(any());

        Exception exception = assertThrows(MigrationErrorOnStepException.class, () -> quadratureSchedTableMigrationStep.executeStep());
        assertTrue(exception.getCause() instanceof RuntimeException);
    }
}
*/
