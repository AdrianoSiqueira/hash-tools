package hashtools.checker.condition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChecksumFileTypeIsValidConditionTest {

    @ParameterizedTest
    @CsvSource({
        "file, true",
        ".file, true",
        "file., true",
        "file.md5, false",
        "file.sha1, false",
        "file.sha224, false",
        "file.sha256, false",
        "file.sha384, false",
        "file.sha512, false",
        "file.txt, false"
    })
    void isFalse(String fileName, boolean expected) {
        assertEquals(
            expected,
            new ChecksumFileTypeIsValidCondition(Path.of(fileName)).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(null).isFalse()
        );
    }

    @ParameterizedTest
    @CsvSource({
        "file, false",
        ".file, false",
        "file., false",
        "file.md5, true",
        "file.sha1, true",
        "file.sha224, true",
        "file.sha256, true",
        "file.sha384, true",
        "file.sha512, true",
        "file.txt, true"
    })
    void isTrue(String fileName, boolean expected) {
        assertEquals(
            expected,
            new ChecksumFileTypeIsValidCondition(Path.of(fileName)).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenFileIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new ChecksumFileTypeIsValidCondition(null).isTrue()
        );
    }
}
