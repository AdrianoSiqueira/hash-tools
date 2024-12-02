package hashtools.shared.messagedigest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringMessageDigestUpdaterTest {

    private static String nullString;
    private static String emptyString;
    private static String string;

    private static MessageDigest messageDigest;

    @BeforeAll
    static void createStrings() {
        nullString = null;
        emptyString = "";
        string = "string";

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void updateDoesNotThrowExceptionWhenStringIsEmpty() {
        assertDoesNotThrow(
            () -> new StringMessageDigestUpdater(emptyString).update(messageDigest)
        );
    }

    @Test
    void updateDoesNotThrowExceptionWhenStringIsNotEmpty() {
        assertDoesNotThrow(
            () -> new StringMessageDigestUpdater(string).update(messageDigest)
        );
    }

    @Test
    void updateThrowsNullPointerExceptionWhenMessageDigestIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new StringMessageDigestUpdater(string).update(null)
        );
    }

    @Test
    void updateThrowsNullPointerExceptionWhenStringIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new StringMessageDigestUpdater(nullString).update(messageDigest)
        );
    }
}
