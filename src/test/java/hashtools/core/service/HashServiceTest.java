package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashServiceTest {

    private HashService service = new HashService();


    @Test
    @DisplayName(value = "Generates the hash from the path object")
    void generate_path() throws IOException {
        Path path = Files.createTempFile(null, null);
        Files.writeString(path, "123", StandardOpenOption.TRUNCATE_EXISTING);

        assertEquals("202cb962ac59075b964b07152d234b70", service.generate(HashAlgorithm.MD5, path));

        Files.deleteIfExists(path);
    }

    @Test
    @DisplayName(value = "Generates the hash from the sample object")
    void generate_sample() {
        Sample sample = new Sample();
        sample.setAlgorithm(HashAlgorithm.MD5);
        sample.setInputData("123");

        service.generate(sample);

        assertEquals("202cb962ac59075b964b07152d234b70", sample.getCalculatedHash());
    }

    @Test
    @DisplayName(value = "Generates the hash from the string object")
    void generate_string() {
        assertEquals("202cb962ac59075b964b07152d234b70", service.generate(HashAlgorithm.MD5, "123"));
    }
}
