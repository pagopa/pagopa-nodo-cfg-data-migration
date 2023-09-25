package it.gov.pagopa.apiconfig.datamigration.enumeration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum StepName {

    START,
    EXECUTE_GENERIC_TABLE_MIGRATION,
    ERROR,
    END;
}
