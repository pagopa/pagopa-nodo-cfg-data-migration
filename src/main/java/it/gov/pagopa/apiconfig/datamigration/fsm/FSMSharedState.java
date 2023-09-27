package it.gov.pagopa.apiconfig.datamigration.fsm;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FSMSharedState {

    private boolean isBlockRequested;

    private boolean isInLock;

    @Setter
    private String dataMigrationStateId;

    public void resetStates() {
        this.isBlockRequested = false;
        this.dataMigrationStateId = null;
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
