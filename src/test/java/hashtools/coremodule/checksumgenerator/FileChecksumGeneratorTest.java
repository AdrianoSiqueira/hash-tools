package hashtools.coremodule.checksumgenerator;

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
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileChecksumGeneratorTest {

    private static Path nullFile;
    private static Path noPathFile;
    private static Path regularFile;
    private static Path folder;
    private static Path nonExistentFile;

    private static Algorithm nullAlgorithm;
    private static Algorithm md5Algorithm;


    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(regularFile);
        Files.deleteIfExists(folder);
    }

    static Stream<Arguments> getExceptionTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullAlgorithm, regularFile),
            Arguments.of(true, NullPointerException.class, md5Algorithm, nullFile),
            Arguments.of(true, IOException.class, md5Algorithm, noPathFile),
            Arguments.of(true, IOException.class, md5Algorithm, folder),
            Arguments.of(true, NoSuchFileException.class, md5Algorithm, nonExistentFile),
            Arguments.of(false, null, md5Algorithm, regularFile)
        );
    }

    static Stream<Arguments> getResultTests() {
        return Stream.of(
            Arguments.of(regularFile, Algorithm.MD5, "d41d8cd98f00b204e9800998ecf8427e"),
            Arguments.of(regularFile, Algorithm.SHA1, "da39a3ee5e6b4b0d3255bfef95601890afd80709"),
            Arguments.of(regularFile, Algorithm.SHA224, "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f"),
            Arguments.of(regularFile, Algorithm.SHA256, "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
            Arguments.of(regularFile, Algorithm.SHA384, "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b"),
            Arguments.of(regularFile, Algorithm.SHA512, "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e")
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullFile = null;
        noPathFile = Path.of("");
        folder = Files.createTempDirectory("_folder_");
        regularFile = Files.createTempFile("_file_", "");

        nonExistentFile = Files.createTempFile("_non_existent_", "");
        Files.deleteIfExists(nonExistentFile);


        nullAlgorithm = null;
        md5Algorithm = Algorithm.MD5;
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void generateReturns(Path file, Algorithm algorithm, String result)
    throws IOException, NoSuchAlgorithmException {
        assertEquals(
            result,
            new FileChecksumGenerator(algorithm, file).generate()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTests")
    void generateThrows(boolean shouldThrow, Class<? extends Throwable> throwable, Algorithm algorithm, Path file) {
        Executable executable = () -> new FileChecksumGenerator(algorithm, file).generate();

        if (shouldThrow) {
            assertThrows(throwable, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
