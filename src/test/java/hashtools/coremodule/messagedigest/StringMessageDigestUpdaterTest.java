package hashtools.coremodule.messagedigest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.security.MessageDigest;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringMessageDigestUpdaterTest {

    private static String
        nullString,
        emptyString,
        regularString;

    private static MessageDigest
        nullMessageDigest,
        messageDigest;


    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullMessageDigest, regularString),
            Arguments.of(true, NullPointerException.class, messageDigest, nullString),
            Arguments.of(false, null, messageDigest, emptyString),
            Arguments.of(false, null, messageDigest, regularString)
        );
    }

    @BeforeAll
    static void setup() throws Exception {
        nullString = null;
        emptyString = "";
        regularString = "string";

        nullMessageDigest = null;
        messageDigest = MessageDigest.getInstance("MD5");
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void update(boolean shouldThrow, Class<? extends Throwable> expectedException, MessageDigest messageDigest, String string) {
        Executable executable = () -> new StringMessageDigestUpdater(string).update(messageDigest);

        if (shouldThrow) {
            assertThrows(expectedException, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
