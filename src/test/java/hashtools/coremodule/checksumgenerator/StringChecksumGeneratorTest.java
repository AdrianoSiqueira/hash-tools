package hashtools.coremodule.checksumgenerator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringChecksumGeneratorTest {

    private static String nullString;
    private static String emptyString;
    private static String regularString;

    private static Algorithm nullAlgorithm;
    private static Algorithm md5Algorithm;

    static Stream<Arguments> getExceptionTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullAlgorithm, regularString),
            Arguments.of(true, NullPointerException.class, md5Algorithm, nullString),
            Arguments.of(false, null, md5Algorithm, emptyString),
            Arguments.of(false, null, md5Algorithm, regularString)
        );
    }

    static Stream<Arguments> getResultTests() {
        return Stream.of(
            Arguments.of(emptyString, Algorithm.MD5, "d41d8cd98f00b204e9800998ecf8427e"),
            Arguments.of(emptyString, Algorithm.SHA1, "da39a3ee5e6b4b0d3255bfef95601890afd80709"),
            Arguments.of(emptyString, Algorithm.SHA224, "d14a028c2a3a2bc9476102bb288234c415a2b01f828ea62ac5b3e42f"),
            Arguments.of(emptyString, Algorithm.SHA256, "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"),
            Arguments.of(emptyString, Algorithm.SHA384, "38b060a751ac96384cd9327eb1b1e36a21fdb71114be07434c0cc7bf63f6e1da274edebfe76f65fbd51ad2f14898b95b"),
            Arguments.of(emptyString, Algorithm.SHA512, "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e"),

            Arguments.of(regularString, Algorithm.MD5, "0cc175b9c0f1b6a831c399e269772661"),
            Arguments.of(regularString, Algorithm.SHA1, "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8"),
            Arguments.of(regularString, Algorithm.SHA224, "abd37534c7d9a2efb9465de931cd7055ffdb8879563ae98078d6d6d5"),
            Arguments.of(regularString, Algorithm.SHA256, "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb"),
            Arguments.of(regularString, Algorithm.SHA384, "54a59b9f22b0b80880d8427e548b7c23abd873486e1f035dce9cd697e85175033caa88e6d57bc35efae0b5afd3145f31"),
            Arguments.of(regularString, Algorithm.SHA512, "1f40fc92da241694750979ee6cf582f2d5d7d28e18335de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce8c12cfc7957b2652fe9a75")
        );
    }

    @BeforeAll
    static void setup() {
        nullString = null;
        emptyString = "";
        regularString = "a";


        nullAlgorithm = null;
        md5Algorithm = Algorithm.MD5;
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void generateReturns(String string, Algorithm algorithm, String result)
    throws NoSuchAlgorithmException {
        assertEquals(
            result,
            new StringChecksumGenerator(algorithm, string).generate()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTests")
    void generateThrows(boolean shouldThrow, Class<? extends Throwable> throwable, Algorithm algorithm, String string) {
        Executable executable = () -> new StringChecksumGenerator(algorithm, string).generate();

        if (shouldThrow) {
            assertThrows(throwable, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
