package hashtools.shared.condition;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileIsRegularFileConditionTest {

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
            file = Files.createTempFile("", "");
            folder = Files.createTempDirectory("");

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
    void isFalseReturnsFalseWhenFileIsFile() {
        assertFalse(
            () -> new FileIsRegularFileCondition(file).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileDoesNotExist() {
        assertTrue(
            () -> new FileIsRegularFileCondition(nonExistentFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsEmpty() {
        assertTrue(
            () -> new FileIsRegularFileCondition(emptyFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsFolder() {
        assertTrue(
            () -> new FileIsRegularFileCondition(folder).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileIsRegularFileCondition(nullFile).isFalse()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileDoesNotExist() {
        assertFalse(
            () -> new FileIsRegularFileCondition(nonExistentFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsEmpty() {
        assertFalse(
            () -> new FileIsRegularFileCondition(emptyFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsFolder() {
        assertFalse(
            () -> new FileIsRegularFileCondition(folder).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileIsFile() {
        assertTrue(
            () -> new FileIsRegularFileCondition(file).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileIsRegularFileCondition(nullFile).isTrue()
        );
    }
}
