package hashtools.core.factory;

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
import static org.junit.jupiter.api.Assertions.assertThrows;

class SampleFactoryTest {

    private static Path notExists;
    private static Path empty;
    private static Path filled;


    private SampleFactory factory = new SampleFactory();


    @AfterAll
    static void deleteFiles()
    throws IOException {
        Files.deleteIfExists(empty);
        Files.deleteIfExists(filled);
    }

    @BeforeAll
    static void prepareFiles()
    throws IOException {
        notExists = Files.createTempFile(null, null);
        empty     = Files.createTempFile(null, null);
        filled    = Files.createTempFile(null, null);

        Files.deleteIfExists(notExists);
        Files.writeString(filled, "11111111111111111111111111111111");
    }


    private static List<Arguments> getAlgorithmsExceptionTests() {
        return List.of(
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    private static List<Arguments> getAlgorithmsResultTests() {
        return List.of(
                Arguments.of(List.of(), 0),
                Arguments.of(List.of(""), 0),
                Arguments.of(List.of("", "MD5", "SHA-1", "SHA-224"), 3)
        );
    }

    private static List<Arguments> getOfficialFileExceptionTests() {
        return List.of(
                Arguments.of(notExists, IllegalArgumentException.class)
        );
    }

    private static List<Arguments> getOfficialFileResultTests() {
        return List.of(
                Arguments.of(empty, 0),
                Arguments.of(filled, 1)
        );
    }

    private static List<Arguments> getOfficialHashExceptionTests() {
        return List.of(
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    private static List<Arguments> getOfficialHashResultTests() {
        return List.of(
                Arguments.of("", 0),
                Arguments.of("11111111111111111111111111111111", 1)
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getAlgorithmsExceptionTests")
    @DisplayName(value = "Throw exception when algorithm list is null")
    void createSamples(List<String> algorithms, Class<? extends Throwable> exception) {
        assertThrows(exception, () -> factory.createSamples(algorithms));
    }

    @ParameterizedTest
    @MethodSource(value = "getAlgorithmsResultTests")
    @DisplayName(value = "Create sample list from a list of algorithms")
    void createSamples(List<String> algorithms, int expectedSize) {
        assertEquals(expectedSize, factory.createSamples(algorithms).size());
    }

    @ParameterizedTest
    @MethodSource(value = "getOfficialFileExceptionTests")
    @DisplayName(value = "Throw exception when official file is null")
    void createSamples(Path officialFile, Class<? extends Throwable> exception) {
        assertThrows(exception, () -> factory.createSamples(officialFile));
    }

    @ParameterizedTest
    @MethodSource(value = "getOfficialFileResultTests")
    @DisplayName(value = "Create sample list from the official file")
    void createSamples(Path officialFile, int expectedSize) {
        assertEquals(expectedSize, factory.createSamples(officialFile).size());
    }

    @ParameterizedTest
    @MethodSource(value = "getOfficialHashExceptionTests")
    @DisplayName(value = "Throw exception when official hash is null")
    void createSamples(String officialHash, Class<? extends Throwable> exception) {
        assertThrows(exception, () -> factory.createSamples(officialHash));
    }

    @ParameterizedTest
    @MethodSource(value = "getOfficialHashResultTests")
    @DisplayName(value = "Create sample list from a official hash")
    void createSamples(String officialHash, int expectedSize) {
        assertEquals(expectedSize, factory.createSamples(officialHash).size());
    }
}
