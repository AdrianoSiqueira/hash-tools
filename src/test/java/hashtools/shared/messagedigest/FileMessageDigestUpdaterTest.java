package hashtools.shared.messagedigest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileMessageDigestUpdaterTest {

    private static Path nullFile;
    private static Path emptyFile;
    private static Path file;
    private static Path folder;
    private static Path nonExistentFile;

    private static MessageDigest messageDigest;


    @BeforeAll
    static void createFiles() {
        try {
            nullFile = null;
            emptyFile = Path.of("");
            folder = Files.createTempDirectory("");

            file = Files.createTempFile("", "");
            Files.writeString(file, "a");

            nonExistentFile = Files.createTempFile("", "");
            Files.deleteIfExists(nonExistentFile);

            messageDigest = MessageDigest.getInstance("MD5");
        } catch (IOException | NoSuchAlgorithmException e) {
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
    void updateDoesNotThrowExceptionWhenFileIsFile() {
        assertDoesNotThrow(
            () -> new FileMessageDigestUpdater(file).update(messageDigest)
        );
    }

    @Test
    void updateThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileMessageDigestUpdater(nullFile).update(messageDigest)
        );
    }

    @Test
    void updateThrowsNullPointerExceptionWhenMessageDigestIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileMessageDigestUpdater(file).update(null)
        );
    }

    @Test
    void updateThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new FileMessageDigestUpdater(nonExistentFile).update(messageDigest)
        );
    }

    @Test
    void updateThrowsRuntimeExceptionWhenFileIsEmpty() {
        assertThrows(
            RuntimeException.class,
            () -> new FileMessageDigestUpdater(emptyFile).update(messageDigest)
        );
    }

    @Test
    void updateThrowsRuntimeExceptionWhenFileIsFolder() {
        assertThrows(
            RuntimeException.class,
            () -> new FileMessageDigestUpdater(folder).update(messageDigest)
        );
    }
}
