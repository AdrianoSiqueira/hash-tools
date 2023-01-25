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

class HashServiceTest {

    private static Path nullFile;
    private static Path nonExistentFile;
    private static Path existentFile;

    private HashService service = new HashService();

    @BeforeAll
    static void createFile()
    throws IOException {
        nullFile     = null;
        existentFile = Files.createTempFile(null, null);

        nonExistentFile = Files.createTempFile(null, null);
        Files.deleteIfExists(nonExistentFile);
    }

    private static List<Arguments> getFileTests() {
        return List.of(
                Arguments.of(nullFile, null, null),
                Arguments.of(nullFile, "", null),
                Arguments.of(nullFile, "MD5", null),
                Arguments.of(nonExistentFile, null, null),
                Arguments.of(nonExistentFile, "", null),
                Arguments.of(nonExistentFile, "MD5", null),
                Arguments.of(existentFile, null, null),
                Arguments.of(existentFile, "", null),
                Arguments.of(existentFile, "MD5", "d41d8cd98f00b204e9800998ecf8427e"),
                Arguments.of(existentFile, "SHA-1", "da39a3ee5e6b4b0d3255bfef95601890afd80709"),
                Arguments.of(existentFile, "SHA-224", "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f"),
                Arguments.of(existentFile, "SHA-256", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
                Arguments.of(existentFile, "SHA-384", "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b"),
                Arguments.of(existentFile, "SHA-512", "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e")
        );
    }

    private static List<Arguments> getTextTests() {
        String text = "";

        return List.of(
                Arguments.of(null, null, null),
                Arguments.of(null, null, null),
                Arguments.of(null, null, null),
                Arguments.of(text, null, null),
                Arguments.of(text, "", null),
                Arguments.of(text, "MD5", "d41d8cd98f00b204e9800998ecf8427e"),
                Arguments.of(text, "SHA-1", "da39a3ee5e6b4b0d3255bfef95601890afd80709"),
                Arguments.of(text, "SHA-224", "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f"),
                Arguments.of(text, "SHA-256", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
                Arguments.of(text, "SHA-384", "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b"),
                Arguments.of(text, "SHA-512", "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e")
        );
    }

    @AfterAll
    static void removeFile()
    throws IOException {
        Files.deleteIfExists(existentFile);
    }

    @ParameterizedTest
    @MethodSource(value = "getFileTests")
    @DisplayName(value = "Generates hash checksum from file")
    void generateFile(Path file, String algorithm, String expected) {
        assertEquals(expected, service.generate(algorithm, file));
    }

    @ParameterizedTest
    @MethodSource(value = "getTextTests")
    @DisplayName(value = "Generates hash checksum from text")
    void generateText(String text, String algorithm, String expected) {
        assertEquals(expected, service.generate(algorithm, text));
    }
}
