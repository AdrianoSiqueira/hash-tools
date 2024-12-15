package hashtools.checker.condition;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(false, validFile),
            Arguments.of(true, nullFile),
            Arguments.of(true, noPathFile),
            Arguments.of(true, nonExistentFile),
            Arguments.of(true, folder),
            Arguments.of(true, smallFile),
            Arguments.of(true, bigFile)
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(true, validFile),
            Arguments.of(false, nullFile),
            Arguments.of(false, noPathFile),
            Arguments.of(false, nonExistentFile),
            Arguments.of(false, folder),
            Arguments.of(false, smallFile),
            Arguments.of(false, bigFile)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createDirectory(Path.of(System.getProperty("user.home"), "erase"));
        smallFile = Files.createTempFile("_small_", "");

        nonExistentFile = Files.createTempFile("_non-existent_", "");
        Files.deleteIfExists(nonExistentFile);

        bigFile = Files.createTempFile("_big_", "");
        Files.writeString(bigFile, "A".repeat(THREE_KIBIBYTE + 1));

        validFile = Files.createTempFile("_valid_", "");
        Files.writeString(validFile, "A".repeat(MD5_LENGTH));
    }

    @ParameterizedTest
    @MethodSource(value = "getFalseTests")
    void isFalse(boolean expected, Path file) {
        assertEquals(
            expected,
            new ChecksumFileSizeIsValidCondition(file).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTrueTests")
    void isTrue(boolean expected, Path file) {
        assertEquals(
            expected,
            new ChecksumFileSizeIsValidCondition(file).isTrue()
        );
    }
}
