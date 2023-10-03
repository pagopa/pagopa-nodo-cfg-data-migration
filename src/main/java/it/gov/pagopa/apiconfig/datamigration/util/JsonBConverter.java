package it.gov.pagopa.nodo.datamigration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.AttributeConverter;
import java.io.IOException;

@Slf4j
public class JsonBConverter implements AttributeConverter<DataMigrationDetails, String> {


    @Override
    public String convertToDatabaseColumn(DataMigrationDetails object) {
        String jsonbContent = null;
        try {
            if (object != null) {
                jsonbContent = new ObjectMapper().writeValueAsString(object);
            }
        } catch (final JsonProcessingException e) {
            log.error("Error while converting object to JSONB.", e);
        }

        return jsonbContent;
    }

    @Override
    public DataMigrationDetails convertToEntityAttribute(String jsonbContent) {
        DataMigrationDetails castedContent = null;
        try {
            if (!StringUtils.isBlank(jsonbContent)) {
                castedContent = new ObjectMapper().readValue(jsonbContent, DataMigrationDetails.class);
            }
        } catch (final IOException e) {
            log.error("Error while converting JSONB to object.", e);
        }
        return castedContent;
    }
}
