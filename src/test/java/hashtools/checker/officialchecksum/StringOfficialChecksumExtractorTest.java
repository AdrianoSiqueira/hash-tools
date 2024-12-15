package hashtools.checker.officialchecksum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringOfficialChecksumExtractorTest {

    public static final int MD5_LENGTH = 32;

    private static String
        nullString,
        nonValidChecksum,
        validChecksum;


    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(0, nullString),
            Arguments.of(0, nonValidChecksum),
            Arguments.of(1, validChecksum)
        );
    }

    @BeforeAll
    static void setup() {
        nullString = null;
        nonValidChecksum = "";
        validChecksum = "A".repeat(MD5_LENGTH);
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void extract(int expected, String string) {
        assertEquals(
            expected,
            new StringOfficialChecksumExtractor(string).extract().size()
        );
    }
}
