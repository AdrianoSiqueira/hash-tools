package hashtools.checker.condition;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksumFileSizeIsValidConditionTest {

    private static Path nullFile;
    private static Path noPathFile;
    private static Path folder;
    private static Path emptyFile;
    private static Path filledFile;
    private static Path nonExistentFile;


    @BeforeAll
    static void createFiles() {
        try {
            nullFile = null;
            noPathFile = Path.of("");
            folder = Files.createTempDirectory("");
            emptyFile = Files.createTempFile("", "");

            filledFile = Files.createTempFile("", "");
            Files.writeString(filledFile, "a");

            nonExistentFile = Files.createTempFile("", "");
            Files.deleteIfExists(nonExistentFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void removeFiles() {
        try {
            Files.deleteIfExists(folder);
            Files.deleteIfExists(emptyFile);
            Files.deleteIfExists(filledFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void isFalseReturnsFalseWhenFileHasNoPath() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(noPathFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsFalseWhenFileIsFilled() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(filledFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsFalseWhenFileIsFolder() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(folder).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsEmpty() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(emptyFile).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileSizeIsValidCondition(nullFile).isTrue()
        );
    }

    @Test
    void isFalseThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new ChecksumFileSizeIsValidCondition(nonExistentFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsEmpty() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(emptyFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileHasNoPath() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(noPathFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileIsFilled() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(filledFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileIsFolder() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(folder).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileSizeIsValidCondition(nullFile).isTrue()
        );
    }

    @Test
    void isTrueThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new ChecksumFileSizeIsValidCondition(nonExistentFile).isTrue()
        );
    }
}
