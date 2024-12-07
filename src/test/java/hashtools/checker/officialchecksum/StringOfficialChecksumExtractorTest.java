package hashtools.checker.officialchecksum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringOfficialChecksumExtractorTest {

    private static String
        nullString,
        nonValidChecksum,
        validChecksum;


    @BeforeAll
    static void setup() {
        nullString = null;
        nonValidChecksum = "";
        validChecksum = "A".repeat(32);
    }


    @Test
    void extractReturnsEmptyListWhenStringIsNonValidChecksum() {
        assertTrue(
            new StringOfficialChecksumExtractor(nonValidChecksum)
                .extract()
                .isEmpty()
        );
    }

    @Test
    void extractReturnsOneElementListWhenStringIsValidChecksum() {
        assertEquals(
            1,
            new StringOfficialChecksumExtractor(validChecksum)
                .extract()
                .size()
        );
    }


    @Test
    void extractThrowsNullPointerExceptionWhenStringIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new StringOfficialChecksumExtractor(nullString).extract()
        );
    }
}
