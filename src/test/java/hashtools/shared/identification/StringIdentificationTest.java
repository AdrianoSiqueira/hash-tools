package hashtools.shared.identification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringIdentificationTest {

    private static String
        nullString,
        emptyString,
        regularString;


    static Stream<Arguments> getTests() {
        return Stream.of(
            Arguments.of(nullString),
            Arguments.of(emptyString),
            Arguments.of(regularString)
        );
    }

    @BeforeAll
    static void setup() {
        nullString = null;
        emptyString = "";
        regularString = "string";
    }


    @ParameterizedTest
    @MethodSource(value = "getTests")
    void identify(String string) {
        assertEquals(
            string,
            new StringIdentification(string).identity()
        );
    }
}
