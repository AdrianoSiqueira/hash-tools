package hashtools.utility;

import hashtools.domain.Algorithm;
import hashtools.domain.messagedigest.FileUpdater;
import hashtools.domain.messagedigest.StringUpdater;
import hashtools.domain.messagedigest.Updater;
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

class ChecksumGeneratorTest {
    private static Path   existentFile;
    private static Path   nonExistentFile;
    private static Path   nullFile;
    private static String inputString;

    private ChecksumGenerator service = new ChecksumGenerator();

    @AfterAll
    public static void destroy()
    throws IOException {
        Files.deleteIfExists(existentFile);
    }

    private static Stream<Arguments> getExceptionTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(new FileUpdater(nonExistentFile), RuntimeException.class))
            .add(Arguments.of(new FileUpdater(nullFile), NullPointerException.class))
            .build();
    }

    private static Stream<Arguments> getResultTests() {
        Updater fileUpdater   = new FileUpdater(existentFile);
        Updater stringUpdater = new StringUpdater(inputString);

        String md5    = "d41d8cd98f00b204e9800998ecf8427e";
        String sha1   = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
        String sha224 = "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f";
        String sha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String sha384 = "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b";
        String sha512 = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";

        return Stream
            .<Arguments>builder()
            .add(Arguments.of(Algorithm.MD5, fileUpdater, md5))
            .add(Arguments.of(Algorithm.MD5, stringUpdater, md5))
            .add(Arguments.of(Algorithm.SHA1, fileUpdater, sha1))
            .add(Arguments.of(Algorithm.SHA1, stringUpdater, sha1))
            .add(Arguments.of(Algorithm.SHA224, fileUpdater, sha224))
            .add(Arguments.of(Algorithm.SHA224, stringUpdater, sha224))
            .add(Arguments.of(Algorithm.SHA256, fileUpdater, sha256))
            .add(Arguments.of(Algorithm.SHA256, stringUpdater, sha256))
            .add(Arguments.of(Algorithm.SHA384, fileUpdater, sha384))
            .add(Arguments.of(Algorithm.SHA384, stringUpdater, sha384))
            .add(Arguments.of(Algorithm.SHA512, fileUpdater, sha512))
            .add(Arguments.of(Algorithm.SHA512, stringUpdater, sha512))
            .build();
    }

    @BeforeAll
    public static void init()
    throws IOException {
        nonExistentFile = Files.createTempFile(null, null);
        Files.deleteIfExists(nonExistentFile);

        existentFile = Files.createTempFile(null, null);
        nullFile     = null;
        inputString  = "";
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTests")
    void generate(Updater updater, Class<Throwable> output) {
        assertThrows(output, () -> service.generate(Algorithm.MD5, updater));
    }

    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void generate(Algorithm algorithm, Updater updater, String output) {
        assertEquals(output, service.generate(algorithm, updater));
    }
}
