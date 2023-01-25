package hashtools.core.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileServiceTest {

    private static Path nullFile;
    private static Path nonExistentFile;
    private static Path emptyFile;
    private static Path filledFile;

    private FileService service = new FileService();

    @BeforeAll
    static void createFile()
    throws IOException {
        nullFile  = null;
        emptyFile = Files.createTempFile(null, null);

        filledFile = Files.createTempFile(null, null);
        Files.writeString(filledFile, "A A");

        nonExistentFile = Files.createTempFile(null, null);
        Files.deleteIfExists(nonExistentFile);
    }

    public static List<Arguments> getContentTests() {
        return List.of(
                Arguments.of(nullFile, null, List.of()),
                Arguments.of(nullFile, "", List.of()),
                Arguments.of(nullFile, "A", List.of()),
                Arguments.of(nonExistentFile, null, List.of()),
                Arguments.of(nonExistentFile, "", List.of()),
                Arguments.of(nonExistentFile, "A", List.of("A")),
                Arguments.of(emptyFile, null, List.of()),
                Arguments.of(emptyFile, "", List.of()),
                Arguments.of(emptyFile, "A", List.of("A")),
                Arguments.of(filledFile, null, List.of("A A")),
                Arguments.of(filledFile, "", List.of()),
                Arguments.of(filledFile, "A", List.of("A"))
        );
    }

    public static List<Arguments> getLineTests() {
        return List.of(
                Arguments.of(nullFile, List.of()),
                Arguments.of(nonExistentFile, List.of()),
                Arguments.of(emptyFile, List.of()),
                Arguments.of(filledFile, List.of("A A"))
        );
    }

    public static List<Arguments> getOfficialTests() {
        return List.of(
                Arguments.of(nullFile, List.of()),
                Arguments.of(nonExistentFile, List.of()),
                Arguments.of(emptyFile, List.of()),
                Arguments.of(filledFile, List.of("A", "A"))
        );
    }

    @AfterAll
    static void removeFile()
    throws IOException {
        Files.deleteIfExists(nonExistentFile);
        Files.deleteIfExists(emptyFile);
        Files.deleteIfExists(filledFile);
    }

    @ParameterizedTest
    @MethodSource(value = "getLineTests")
    @DisplayName(value = "Reads the lines of the file")
    void readLines(Path file, List<String> expected) {
        assertEquals(expected, service.readLines(file));
    }

    @ParameterizedTest
    @MethodSource(value = "getOfficialTests")
    @DisplayName(value = "Reads the words of the file")
    void readOfficialFile(Path file, List<String> expected) {
        assertEquals(expected, service.readOfficialFile(file));
    }

    @ParameterizedTest
    @MethodSource(value = "getContentTests")
    @DisplayName(value = "Replaces content in the file")
    void replaceContent(Path file, String content, List<String> expected) {
        service.replaceContent(content, file);

        assertEquals(expected, service.readLines(file));
    }
}
