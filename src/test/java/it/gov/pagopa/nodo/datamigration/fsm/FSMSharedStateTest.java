package it.gov.pagopa.nodo.datamigration.fsm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = FSMSharedState.class)
class FSMSharedStateTest {

    private final FSMSharedState fsmSharedState = new FSMSharedState();

    @Test
    void testRequestBlock() {
        assertFalse(fsmSharedState.isBlockRequested());
        fsmSharedState.requestBlock();
        assertTrue(fsmSharedState.isBlockRequested());
    }

    @Test
    void testLockAndUnlock() {
        assertFalse(fsmSharedState.isInLock());
        fsmSharedState.lock();
        assertTrue(fsmSharedState.isInLock());
        fsmSharedState.unlock();
        assertFalse(fsmSharedState.isInLock());
    }

    @Test
    void testResetStates() {
        fsmSharedState.setDataMigrationStateId("TestID");
        fsmSharedState.requestBlock();
        fsmSharedState.lock();

        fsmSharedState.resetStates();

        assertFalse(fsmSharedState.isBlockRequested());
        assertTrue(fsmSharedState.isInLock());
        assertNull(fsmSharedState.getDataMigrationStateId());
    }

}
