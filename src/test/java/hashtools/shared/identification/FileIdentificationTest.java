package hashtools.shared.identification;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileIdentificationTest {

    private static Path
        nullFile,
        noPathFile,
        regularFile,
        folder,
        nonExistentFile;


    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(regularFile);
        Files.deleteIfExists(folder);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, null, NullPointerException.class, nullFile),
            Arguments.of(false, noPathFile.getFileName().toString(), null, noPathFile),
            Arguments.of(false, regularFile.getFileName().toString(), null, regularFile),
            Arguments.of(false, folder.getFileName().toString(), null, folder),
            Arguments.of(false, nonExistentFile.getFileName().toString(), null, nonExistentFile)
        );
    }

    @BeforeAll
    static void setup() throws IOException {
        nullFile = null;
        noPathFile = Path.of("");
        regularFile = Files.createTempFile("_file_", "");
        folder = Files.createTempDirectory("_folder_");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void identify(boolean shouldThrow, String expectedResult, Class<? extends Throwable> expectedException, Path file) {
        FileIdentification identification = new FileIdentification(file);

        if (shouldThrow) {
            assertThrows(expectedException, identification::identity);
        } else {
            assertEquals(expectedResult, identification.identity());
        }
    }
}
