package it.gov.pagopa.nodo.datamigration.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NumericBooleanConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Boolean value) {
        return Boolean.TRUE.equals(value) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(final Integer value) {
        return value != null && value.equals(1);
    }
}
