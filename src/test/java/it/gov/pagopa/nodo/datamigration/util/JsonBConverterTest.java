package it.gov.pagopa.nodo.datamigration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

class JsonBConverterTest {

    private JsonBConverter jsonBConverter;
    ObjectMapper objectMapper;
    private Logger logger;

    @Before
    void setUp() {
        jsonBConverter = Mockito.spy(new JsonBConverter());
        objectMapper = Mockito.mock(ObjectMapper.class);
        logger = Mockito.mock(Logger.class);
    }

    @Test
    void testConvertToDatabaseColumn() throws IOException {
        DataMigrationDetails details = new DataMigrationDetails();
        details.setPa(DataMigrationStatus
                .builder()
                .status("COMPLETED")
                .end(new Timestamp(1696343803317L))
                .start(new Timestamp(1696343803316L))
                .records(35L)
                .build());
        String expectedJson = TestUtil.readStringFromFile("JsonTest.json");
        String jsonbContent = jsonBConverter.convertToDatabaseColumn(details);
        assertEquals(expectedJson, jsonbContent);
    }

    @Test
    void testConvertToEntityAttribute() throws IOException {
        DataMigrationDetails details = TestUtil.readObjectFromFile("JsonTest.json", DataMigrationDetails.class);
        String stringDetails = TestUtil.readStringFromFile("JsonTest.json");
        DataMigrationDetails detailsContent = jsonBConverter.convertToEntityAttribute(stringDetails);
        assertEquals(details.toString(), detailsContent.toString());
        assertEquals(details.getPa().getStatus(), detailsContent.getPa().getStatus());
        assertEquals(details.getPa().getEnd(), detailsContent.getPa().getEnd());
        assertEquals(details.getPa().getRecords(), detailsContent.getPa().getRecords());
        assertEquals(details.getPa().getStart(), detailsContent.getPa().getStart());

    }

        @Test
        void testConvertToEntityAttributeWithInvalidJson() throws IOException {
            String jsonbContent = ":" + TestUtil.readStringFromFile("JsonTest.json");
            doAnswer(invocation -> {
                System.err.println("Error message: " + invocation.getArgument(0));
                return null;
            }).when(logger).error(anyString(), any(IOException.class));
            DataMigrationDetails detailsContent = jsonBConverter.convertToEntityAttribute(jsonbContent);
            assertNull(detailsContent);
        }
}