package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashServiceTest {

    private HashService service = new HashService();


    @Test
    void generate_path() throws IOException {
        Path path = Files.createTempFile(null, null);
        Files.writeString(path, "123", StandardOpenOption.TRUNCATE_EXISTING);

        assertEquals("202cb962ac59075b964b07152d234b70", service.generate(HashAlgorithm.MD5, path));

        Files.deleteIfExists(path);
    }

    @Test
    void generate_string() {
        assertEquals("202cb962ac59075b964b07152d234b70", service.generate(HashAlgorithm.MD5, "123"));
    }
}
