package hashtools.applicationmodule.checker.condition;

import hashtools.applicationmodule.checker.condition.ChecksumFileTypeIsValidCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChecksumFileTypeIsValidConditionTest {

    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(true, null),
            Arguments.of(true, Path.of("")),
            Arguments.of(true, Path.of("file")),
            Arguments.of(true, Path.of(".file")),
            Arguments.of(true, Path.of("file.")),
            Arguments.of(false, Path.of("file.md5")),
            Arguments.of(false, Path.of("file.sha1")),
            Arguments.of(false, Path.of("file.sha224")),
            Arguments.of(false, Path.of("file.sha256")),
            Arguments.of(false, Path.of("file.sha384")),
            Arguments.of(false, Path.of("file.sha512")),
            Arguments.of(false, Path.of("file.txt"))
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(false, null),
            Arguments.of(false, Path.of("")),
            Arguments.of(false, Path.of("file")),
            Arguments.of(false, Path.of(".file")),
            Arguments.of(false, Path.of("file.")),
            Arguments.of(true, Path.of("file.md5")),
            Arguments.of(true, Path.of("file.sha1")),
            Arguments.of(true, Path.of("file.sha224")),
            Arguments.of(true, Path.of("file.sha256")),
            Arguments.of(true, Path.of("file.sha384")),
            Arguments.of(true, Path.of("file.sha512")),
            Arguments.of(true, Path.of("file.txt"))
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getFalseTests")
    void isFalse(boolean expected, Path file) {
        assertEquals(
            expected,
            new ChecksumFileTypeIsValidCondition(file).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTrueTests")
    void isTrue(boolean expected, Path file) {
        assertEquals(
            expected,
            new ChecksumFileTypeIsValidCondition(file).isTrue()
        );
    }
}
