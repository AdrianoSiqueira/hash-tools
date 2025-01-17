package hashtools.applicationmodule.checker.officialchecksum;

import hashtools.applicationmodule.checker.officialchecksum.FileOfficialChecksumExtractor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileOfficialChecksumExtractorTest {

    public static final int MD5_LENGTH = 32;
    public static final int SHA1_LENGTH = 40;

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

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(0, nullFile),
            Arguments.of(0, noPathFile),
            Arguments.of(0, nonExistentFile),
            Arguments.of(0, folder),
            Arguments.of(0, noChecksumFile),
            Arguments.of(1, oneChecksumFile),
            Arguments.of(2, multiChecksumFile)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createTempDirectory("_folder_");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);

        noChecksumFile = Files.createTempFile("_zero_", "");
        Files.writeString(noChecksumFile, "A");

        oneChecksumFile = Files.createTempFile("_one_", "");
        Files.writeString(oneChecksumFile, "A".repeat(MD5_LENGTH));

        multiChecksumFile = Files.createTempFile("_two_", "");
        Files.writeString(multiChecksumFile, "A".repeat(MD5_LENGTH).concat("\n"), StandardOpenOption.APPEND);
        Files.writeString(multiChecksumFile, "A".repeat(SHA1_LENGTH), StandardOpenOption.APPEND);
    }

    @ParameterizedTest
    @MethodSource(value = "getTests")
    void extract(int expected, Path file) {
        assertEquals(
            expected,
            new FileOfficialChecksumExtractor(file).extract().size()
        );
    }
}
