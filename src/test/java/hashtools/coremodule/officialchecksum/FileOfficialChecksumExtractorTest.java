package hashtools.coremodule.officialchecksum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileOfficialChecksumExtractorTest {

    public static final int MD5_LENGTH = 32;
    public static final int SHA1_LENGTH = 40;

    private static Path nullFile;
    private static Path noPathFile;
    private static Path nonExistentFile;
    private static Path folder;
    private static Path noChecksumFile;
    private static Path oneChecksumFile;
    private static Path multiChecksumFile;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(noChecksumFile);
        Files.deleteIfExists(oneChecksumFile);
        Files.deleteIfExists(multiChecksumFile);
    }

    static Stream<Arguments> getExceptionTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullFile),
            Arguments.of(true, IOException.class, noPathFile),
            Arguments.of(true, IOException.class, nonExistentFile),
            Arguments.of(true, IOException.class, folder),
            Arguments.of(false, null, noChecksumFile),
            Arguments.of(false, null, oneChecksumFile),
            Arguments.of(false, null, multiChecksumFile)
        );
    }

    static Stream<Arguments> getResultTests() {
        return Stream.of(
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
        noChecksumFile = Files.createTempFile("_zero_", "");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);

        oneChecksumFile = Files.createTempFile("_one_", "");
        Files.writeString(oneChecksumFile, "A".repeat(MD5_LENGTH));

        multiChecksumFile = Files.createTempFile("_two_", "");
        Files.writeString(multiChecksumFile, "A".repeat(MD5_LENGTH).concat("\n"), StandardOpenOption.APPEND);
        Files.writeString(multiChecksumFile, "A".repeat(SHA1_LENGTH), StandardOpenOption.APPEND);
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void extractReturns(int result, Path file)
    throws IOException {
        assertEquals(
            result,
            new FileOfficialChecksumExtractor(file).extract().size()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTests")
    void extractThrows(boolean shouldThrow, Class<? extends Throwable> throwable, Path file) {
        Executable executable = () -> new FileOfficialChecksumExtractor(file).extract();

        if (shouldThrow) {
            assertThrows(throwable, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
