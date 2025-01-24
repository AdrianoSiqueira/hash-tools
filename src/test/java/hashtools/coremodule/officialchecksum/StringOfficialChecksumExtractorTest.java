package hashtools.coremodule.officialchecksum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringOfficialChecksumExtractorTest {

    public static final int MD5_LENGTH = 32;

    private static String nullString;
    private static String emptyString;
    private static String regularString;


    static Stream<Arguments> getExceptionTests() {
        return Stream.of(
            Arguments.of(true, NullPointerException.class, nullString),
            Arguments.of(false, null, emptyString),
            Arguments.of(false, null, regularString)
        );
    }

    static Stream<Arguments> getResultTests() {
        return Stream.of(
            Arguments.of(1, emptyString),
            Arguments.of(1, regularString)
        );
    }

    @BeforeAll
    static void setup() {
        nullString = null;
        emptyString = "";
        regularString = "A".repeat(MD5_LENGTH);
    }


    @ParameterizedTest
    @MethodSource(value = "getResultTests")
    void extractReturns(int result, String string) {
        assertEquals(
            result,
            new StringOfficialChecksumExtractor(string).extract().size()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionTests")
    void extractThrows(boolean shouldThrow, Class<? extends Throwable> throwable, String string) {
        Executable executable = () -> new StringOfficialChecksumExtractor(string).extract();

        if (shouldThrow) {
            assertThrows(throwable, executable);
        } else {
            assertDoesNotThrow(executable);
        }
    }
}
