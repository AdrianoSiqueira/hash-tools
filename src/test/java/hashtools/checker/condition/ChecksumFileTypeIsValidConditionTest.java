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

class ChecksumFileTypeIsValidConditionTest {

    private static Path nullFile;
    private static Path noPathFile;
    private static Path folder;
    private static Path emptyFile;
    private static Path textFile, nonTextFile;
    private static Path nonExistentFile;


    @BeforeAll
    static void createFiles() {
        try {
            nullFile = null;
            noPathFile = Path.of("");
            folder = Files.createTempDirectory("");
            emptyFile = Files.createTempFile("", "");

            textFile = Files.createTempFile("", ".txt");
            nonTextFile = Files.createTempFile("", ".jpg");

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
            Files.deleteIfExists(textFile);
            Files.deleteIfExists(nonTextFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void isFalseReturnsFalseWhenFileIsTextFile() {
        assertFalse(
            new ChecksumFileTypeIsValidCondition(textFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsNotTextFile() {
        assertTrue(
            new ChecksumFileTypeIsValidCondition(nonTextFile).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileHasNoPath() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(noPathFile).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsEmpty() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(emptyFile).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsFolder() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(folder).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(nullFile).isFalse()
        );
    }

    @Test
    void isFalseThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new ChecksumFileTypeIsValidCondition(nonExistentFile).isFalse()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsNotTextFile() {
        assertFalse(
            new ChecksumFileTypeIsValidCondition(nonTextFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileIsTextFile() {
        assertTrue(
            new ChecksumFileTypeIsValidCondition(textFile).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileHasNoPath() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(noPathFile).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsEmpty() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(emptyFile).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsFolder() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(folder).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(nullFile).isTrue()
        );
    }

    @Test
    void isTrueThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new ChecksumFileTypeIsValidCondition(nonExistentFile).isTrue()
        );
    }
}
