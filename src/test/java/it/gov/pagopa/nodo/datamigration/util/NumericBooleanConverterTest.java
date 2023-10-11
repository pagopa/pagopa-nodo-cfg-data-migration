package it.gov.pagopa.nodo.datamigration.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumericBooleanConverterTest {

    private NumericBooleanConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NumericBooleanConverter();
    }

    @Test
    void testConvertToDatabaseColumnTrue() {
        assertEquals(1, converter.convertToDatabaseColumn(Boolean.TRUE));
    }

    @Test
    void testConvertToDatabaseColumnFalse() {
        assertEquals(0, converter.convertToDatabaseColumn(Boolean.FALSE));
    }

    @Test
    void testConvertToEntityAttributeTrue() {
        assertEquals(Boolean.TRUE, converter.convertToEntityAttribute(1));
    }

    @Test
    void testConvertToEntityAttributeFalse() {
        assertEquals(Boolean.FALSE, converter.convertToEntityAttribute(0));
    }

    @Test
    void testConvertToEntityAttributeWithNull() {
        assertEquals(Boolean.FALSE, converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttributeInvalidValue() {
        assertEquals(Boolean.FALSE, converter.convertToEntityAttribute(2));
    }
}