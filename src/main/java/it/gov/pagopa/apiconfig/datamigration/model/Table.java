package it.gov.pagopa.apiconfig.datamigration.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;

@Data
public class Table {
    @Id
    private String tableName;
    private List<String> columns;
    private List<List<Object>> records;
    private int pageNumber;
}
