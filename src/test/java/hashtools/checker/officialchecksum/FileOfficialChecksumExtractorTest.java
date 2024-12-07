package hashtools.checker.officialchecksum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileOfficialChecksumExtractorTest {

    private static Path
        nullFile,
        noPathFile,
        nonExistentFile,
        folder,
        noChecksumFile,
        oneChecksumFile,
        multiChecksumFile;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(noChecksumFile);
        Files.deleteIfExists(oneChecksumFile);
        Files.deleteIfExists(multiChecksumFile);
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createTempDirectory("");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);

        noChecksumFile = Files.createTempFile("", "");
        Files.writeString(noChecksumFile, "A");

        oneChecksumFile = Files.createTempFile("", "");
        Files.writeString(oneChecksumFile, "A".repeat(32));

        multiChecksumFile = Files.createTempFile("", "");
        Files.writeString(multiChecksumFile, "A".repeat(32).concat("\n"), StandardOpenOption.APPEND);
        Files.writeString(multiChecksumFile, "A".repeat(40), StandardOpenOption.APPEND);
    }


    @Test
    void extractReturnsEmptyListWhenFileHasNoChecksum() {
        assertTrue(
            new FileOfficialChecksumExtractor(noChecksumFile)
                .extract()
                .isEmpty()
        );
    }

    @Test
    void extractReturnsMoreThanOneElementListWhenFileHasMoreThanOneChecksum() {
        assertTrue(
            new FileOfficialChecksumExtractor(multiChecksumFile)
                .extract()
                .size()
                > 1
        );
    }

    @Test
    void extractReturnsOneElementListWhenFileHasOneChecksum() {
        assertEquals(
            1,
            new FileOfficialChecksumExtractor(oneChecksumFile)
                .extract()
                .size()
        );
    }

    @Test
    void extractThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new FileOfficialChecksumExtractor(nullFile).extract()
        );
    }

    @Test
    void extractThrowsRuntimeExceptionWhenFileDoesNotExist() {
        assertThrows(
            RuntimeException.class,
            () -> new FileOfficialChecksumExtractor(nonExistentFile).extract()
        );
    }

    @Test
    void extractThrowsRuntimeExceptionWhenFileHasNoPath() {
        assertThrows(
            RuntimeException.class,
            () -> new FileOfficialChecksumExtractor(noPathFile).extract()
        );
    }

    @Test
    void extractThrowsRuntimeExceptionWhenFileIsFoder() {
        assertThrows(
            RuntimeException.class,
            () -> new FileOfficialChecksumExtractor(folder).extract()
        );
    }
}
