package it.gov.pagopa.apiconfig.datamigration.fsm;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FSMSharedState {

    @Setter
    private boolean isPersistingData;

    private boolean isBlockRequested;

    private boolean isInLock;

    public void resetStates() {
        this.isBlockRequested = false;
        this.isPersistingData = false;
    }

    public void requestBlock() {
        this.isBlockRequested = true;
    }

    public void lock() {
        this.isInLock = true;
    }

    public void unlock() {
        this.isInLock = false;
    }
}
