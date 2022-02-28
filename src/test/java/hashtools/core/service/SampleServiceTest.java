package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleServiceTest {

    private SampleService service = new SampleService();


    @Test
    void createSampleFromAlgorithm_returnEmptyOptionalWhenAlgorithmIsNull() {
        assertTrue(service.createSampleFromAlgorithm(null).isEmpty());
    }

    @Test
    void createSampleFromAlgorithm_returnFilledOptionalWhenAlgorithmIsNotNull() {
        assertTrue(service.createSampleFromAlgorithm(HashAlgorithm.MD5).isPresent());
    }


    @Test
    void createSampleList_returnEmptyListWhenFileHasNoValidHashes() throws IOException {
        Path path = Files.createTempFile(null, null);

        assertTrue(service.createSampleList(path.toAbsolutePath().toString()).isEmpty());

        Files.deleteIfExists(path);
    }

    @Test
    void createSampleList_returnEmptyListWhenFileIsNull() {
        assertTrue(service.createSampleList(null).isEmpty());
    }

    @Test
    void createSampleList_returnEmptyListWhenHashIsNotValid() {
        assertTrue(service.createSampleList("").isEmpty());
    }

    @Test
    void createSampleList_returnEmptyListWhenHashIsNull() {
        assertTrue(service.createSampleList(null).isEmpty());
    }

    @Test
    void createSampleList_returnFilledListWhenFileHasValidHashes() throws IOException {
        Path path = Files.createTempFile(null, null);
        Files.writeString(path, "11111111111111111111111111111111");

        assertFalse(service.createSampleList(path.toAbsolutePath().toString()).isEmpty());

        Files.deleteIfExists(path);
    }

    @Test
    void createSampleList_returnSingleElementListWhenHashIsValid() {
        assertEquals(1, service.createSampleList("11111111111111111111111111111111").size());
    }
}
