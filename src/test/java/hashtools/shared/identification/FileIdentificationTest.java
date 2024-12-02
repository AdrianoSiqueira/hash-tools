package hashtools.shared.identification;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileIdentificationTest {

    private static Path nullFile;
    private static Path emptyFile;
    private static Path file;
    private static Path folder;
    private static Path nonExistentFile;


    @BeforeAll
    static void createFiles() {
        try {
            nullFile = null;
            emptyFile = Path.of("");
            file = Files.createTempFile("a", "");
            folder = Files.createTempDirectory("a");

            nonExistentFile = Files.createTempFile("", "");
            Files.deleteIfExists(nonExistentFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void removeFiles() {
        try {
            Files.deleteIfExists(file);
            Files.deleteIfExists(folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void identifyReturnsFileNameWhenFileDoesNotExist() {
        assertEquals(
            nonExistentFile.getFileName().toString(),
            new FileIdentification(nonExistentFile).identity()
        );
    }

    @Test
    void identifyReturnsFileNameWhenFileIsEmpty() {
        assertEquals(
            emptyFile.getFileName().toString(),
            new FileIdentification(emptyFile).identity()
        );
    }

    @Test
    void identifyReturnsFileNameWhenFileIsFile() {
        assertEquals(
            file.getFileName().toString(),
            new FileIdentification(file).identity()
        );
    }

    @Test
    void identifyReturnsFileNameWhenFileIsFolder() {
        assertEquals(
            folder.getFileName().toString(),
            new FileIdentification(folder).identity()
        );
    }

    @Test
    void identifyThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileIdentification(nullFile).identity()
        );
    }
}
