package hashtools.core.service;

import hashtools.core.model.FileExtension;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileServiceTest {

    private FileService service = new FileService();


    @Test
    void pathHasRequiredExtension_returnFalseWhenPathHasNotRequiredExtension() {
        String tempDir = System.getProperty("java.io.tmpdir");

        assertFalse(service.pathHasRequiredExtension(Path.of(tempDir, "file.jpg"), FileExtension.HASH), "'HASH' failed");
    }

    @Test
    void pathHasRequiredExtension_returnTrueWhenPathHasRequiredExtension() {
        assertTrue(service.pathHasRequiredExtension(Path.of("file.jpg"), FileExtension.ALL), "'ALL' failed");
        assertTrue(service.pathHasRequiredExtension(Path.of("file"), FileExtension.ALL), "'ALL (without extension)' failed");
        assertTrue(service.pathHasRequiredExtension(Path.of("file.txt"), FileExtension.HASH), "'HASH' failed");
    }

    @Test
    void pathHasRequiredExtension_throwsWhenSomeDataIsNull() {
        assertThrows(NullPointerException.class, () -> service.pathHasRequiredExtension(null, null), "Both failed");
        assertThrows(NullPointerException.class, () -> service.pathHasRequiredExtension(null, FileExtension.ALL), "Path failed");
        assertThrows(NullPointerException.class, () -> service.pathHasRequiredExtension(Path.of("a.txt"), null), "FileExtension failed");
    }


    @Test
    void stringIsFilePath_returnFalseWhenStringDoesNotMatchAFilePath() {
        assertFalse(service.stringIsFilePath(""));
    }

    @Test
    void stringIsFilePath_returnFalseWhenStringIsNull() {
        assertFalse(service.stringIsFilePath(null));
    }

    @Test
    void stringIsFilePath_returnTrueWhenStringMatchesAFilePath() throws IOException {
        Path path = Files.createTempFile(null, null);

        assertTrue(service.stringIsFilePath(path.toAbsolutePath().toString()));

        Files.deleteIfExists(path);
    }


    @Test
    void write_writeContentToAnExistingFile() throws IOException {
        Path path = Files.createTempFile(null, null);

        new FileService().write("123", path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        assertEquals(1, Files.readAllLines(path).size());

        Files.deleteIfExists(path);
    }

    @Test
    void write_writeContentToAnNonExistingFile() throws IOException {
        Path path = Files.createTempFile(null, null);
        Files.deleteIfExists(path);

        new FileService().write("123", path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        assertTrue(Files.isRegularFile(path), "File not created");

        Files.deleteIfExists(path);
    }
}
