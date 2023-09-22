package it.gov.pagopa.apiconfig.datamigration.fsm;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FSMSharedState {

    private boolean isPersistingData;

    private boolean isInLock;
}
