package hashtools.checker.condition;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChecksumFileSizeIsValidConditionTest {

    private static final int THREE_KIBIBYTE = 3072;
    private static final int MD5_LENGTH = 32;

    private static Path
        nullFile,
        noPathFile,
        nonExistentFile,
        folder,
        smallFile,
        bigFile,
        validFile;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(smallFile);
        Files.deleteIfExists(bigFile);
        Files.deleteIfExists(validFile);
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createDirectory(Path.of(System.getProperty("user.home"), "erase"));
        smallFile = Files.createTempFile("", "");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);

        bigFile = Files.createTempFile("", "");
        Files.writeString(bigFile, "A".repeat(THREE_KIBIBYTE + 1));

        validFile = Files.createTempFile("", "");
        Files.writeString(validFile, "A".repeat(MD5_LENGTH));
    }


    @Test
    void isFalseReturnsFalseWhenFileIsValid() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(validFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileDoesNotExist() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(nonExistentFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileHasNoPath() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(noPathFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsFolder() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(folder).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsNull() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(nullFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsTooBig() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(bigFile).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenFileIsTooSmall() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(smallFile).isFalse()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileDoesNotExist() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(nonExistentFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileHasNoPath() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(noPathFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsFolder() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(folder).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsNull() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(nullFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsTooBig() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(bigFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenFileIsTooSmall() {
        assertFalse(
            new ChecksumFileSizeIsValidCondition(smallFile).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenFileIsValid() {
        assertTrue(
            new ChecksumFileSizeIsValidCondition(validFile).isTrue()
        );
    }
}
