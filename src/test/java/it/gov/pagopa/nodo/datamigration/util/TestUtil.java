package it.gov.pagopa.nodo.datamigration.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
@UtilityClass
class TestUtil {
    String readStringFromFile(String relativePath) throws IOException {
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(relativePath)).getPath());
        return Files.readString(file.toPath());
    }

    File readFile(String relativePath) {
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(relativePath)).getFile());
    }

    <T> T readObjectFromFile(String relativePath, Class<T> valueType) throws IOException {
        var jsonFile = readFile(relativePath);
        return new ObjectMapper().readValue(jsonFile, valueType);
    }
}
