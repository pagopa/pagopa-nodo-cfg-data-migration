package it.gov.pagopa.nodo.datamigration.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class YesNoConverter implements AttributeConverter<Boolean, String> {

    public static final String YES = "Y";
    public static final String NO = "N";

    @Override
    public String convertToDatabaseColumn(final Boolean value) {
        return Boolean.TRUE.equals(value) ? YES : NO;
    }

    @Override
    public Boolean convertToEntityAttribute(final String value) {
        return YES.equals(value);
    }
}
