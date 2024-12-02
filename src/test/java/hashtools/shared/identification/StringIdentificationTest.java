package hashtools.shared.identification;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringIdentificationTest {

    private static String nullString;
    private static String emptyString;
    private static String string;


    @BeforeAll
    static void createStrings() {
        nullString = null;
        emptyString = "";
        string = "string";
    }

    @Test
    void identifyReturnsTheStringItselfWhenStringIsEmpty() {
        assertEquals(
            emptyString,
            new StringIdentification(emptyString).identity()
        );
    }

    @Test
    void identifyReturnsTheStringItselfWhenStringIsNotEmpty() {
        assertEquals(
            string,
            new StringIdentification(string).identity()
        );
    }

    @Test
    void identifyReturnsTheStringItselfWhenStringIsNull() {
        assertEquals(
            nullString,
            new StringIdentification(nullString).identity()
        );
    }
}
