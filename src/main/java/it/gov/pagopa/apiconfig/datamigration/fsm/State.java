package it.gov.pagopa.apiconfig.datamigration.fsm;

import it.gov.pagopa.apiconfig.datamigration.fsm.step.EndStep;
import it.gov.pagopa.apiconfig.datamigration.fsm.step.ExecuteGenericTableMigrationStep;
import it.gov.pagopa.apiconfig.datamigration.fsm.step.StartStep;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public enum State {

    START(StartStep.class),
    EXECUTE_GENERIC_TABLE_MIGRATION(ExecuteGenericTableMigrationStep.class),
    END(EndStep.class);

    private Step operation;

    <S extends Step> State(Class<S> clazz)  {
        try {
            this.operation = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            this.operation = new EndStep();
        }
    }

    public State execute(FSMSharedState sharedState) throws Exception {
        this.operation.attachSharedState(sharedState);
        return this.operation.call();
    }
}
