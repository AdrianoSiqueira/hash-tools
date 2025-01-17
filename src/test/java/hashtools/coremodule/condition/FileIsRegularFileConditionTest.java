package hashtools.coremodule.condition;

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

class FileIsRegularFileConditionTest {

    private static Path nullFile;
    private static Path noPathFile;
    private static Path nonExistentFile;
    private static Path folder;
    private static Path emptyFile;
    private static Path textFile;


    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(folder);
        Files.deleteIfExists(emptyFile);
        Files.deleteIfExists(textFile);
    }

    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(false, emptyFile),
            Arguments.of(false, textFile),
            Arguments.of(true, nullFile),
            Arguments.of(true, noPathFile),
            Arguments.of(true, nonExistentFile),
            Arguments.of(true, folder)
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(true, emptyFile),
            Arguments.of(true, textFile),
            Arguments.of(false, nullFile),
            Arguments.of(false, noPathFile),
            Arguments.of(false, nonExistentFile),
            Arguments.of(false, folder)
        );
    }

    @BeforeAll
    static void setup() throws IOException {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createTempDirectory("_folder_");
        emptyFile = Files.createTempFile("_empty_", "");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);

        textFile = Files.createTempFile("_text_", ".txt");
        Files.writeString(textFile, "123");
    }


    @ParameterizedTest
    @MethodSource("getFalseTests")
    void isFalse(boolean expected, Path file) {
        assertEquals(
            expected,
            new FileIsRegularFileCondition(file).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource("getTrueTests")
    void isTrue(boolean expected, Path file) {
        assertEquals(
            expected,
            new FileIsRegularFileCondition(file).isTrue()
        );
    }
}
