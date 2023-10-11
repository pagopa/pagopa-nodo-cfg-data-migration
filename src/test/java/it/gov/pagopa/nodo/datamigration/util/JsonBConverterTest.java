package it.gov.pagopa.nodo.datamigration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationDetails;
import it.gov.pagopa.nodo.datamigration.entity.DataMigrationStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Timestamp;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = JsonBConverterTest.class)
class JsonBConverterTest {

    private JsonBConverter jsonBConverter;
    ObjectMapper objectMapper;
    private Logger logger;

    @BeforeEach
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
        Assertions.assertEquals(expectedJson, jsonbContent);
    }

    @Test
    void testConvertToEntityAttribute() throws IOException {
        DataMigrationDetails details = TestUtil.readObjectFromFile("JsonTest.json", DataMigrationDetails.class);
        String stringDetails = TestUtil.readStringFromFile("JsonTest.json");
        DataMigrationDetails detailsContent = jsonBConverter.convertToEntityAttribute(stringDetails);
        Assertions.assertEquals(details.toString(), detailsContent.toString());
        Assertions.assertEquals(details.getPa().getStatus(), detailsContent.getPa().getStatus());
        Assertions.assertEquals(details.getPa().getEnd(), detailsContent.getPa().getEnd());
        Assertions.assertEquals(details.getPa().getRecords(), detailsContent.getPa().getRecords());
        Assertions.assertEquals(details.getPa().getStart(), detailsContent.getPa().getStart());

    }

        @Test
        void testConvertToEntityAttributeWithInvalidJson() throws IOException {
            String jsonbContent = ":" + TestUtil.readStringFromFile("JsonTest.json");
            doAnswer(invocation -> {
                System.err.println("Error message: " + invocation.getArgument(0));
                return null;
            }).when(logger).error(anyString(), any(IOException.class));
            DataMigrationDetails detailsContent = jsonBConverter.convertToEntityAttribute(jsonbContent);
            Assertions.assertNull(detailsContent);
        }
}