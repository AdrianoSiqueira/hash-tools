package hashtools.coremodule.messagedigest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileMessageDigestUpdaterTest {

    private static Path
        nullFile,
        noPathFile,
        regularFile,
        folder,
        nonExistentFile;

    private static MessageDigest
        nullMessageDigest,
        messageDigest;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(regularFile);
        Files.deleteIfExists(folder);
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullMessageDigest, regularFile),
            Arguments.of(true, NullPointerException.class, messageDigest, nullFile),
            Arguments.of(true, IOException.class, messageDigest, noPathFile),
            Arguments.of(true, IOException.class, messageDigest, folder),
            Arguments.of(true, NoSuchFileException.class, messageDigest, nonExistentFile),
            Arguments.of(false, null, messageDigest, regularFile)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createTempDirectory("_folder_");

        regularFile = Files.createTempFile("_file_", "");
        Files.writeString(regularFile, "a");

        nonExistentFile = Files.createTempFile("", "");
        Files.deleteIfExists(nonExistentFile);

        nullMessageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void update(boolean shouldThrow, Class<? extends Throwable> expectedException, MessageDigest messageDigest, Path file) {
        Executable executable = () -> new FileMessageDigestUpdater(file).update(messageDigest);

        if (shouldThrow) {
            assertThrows(expectedException, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
