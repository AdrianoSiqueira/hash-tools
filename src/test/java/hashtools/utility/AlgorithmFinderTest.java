package hashtools.utility;

import hashtools.domain.Algorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmFinderTest {

    private AlgorithmFinder finder = new AlgorithmFinder();

    public static Stream<Arguments> getLengthTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(0, null))
            .add(Arguments.of(32, Algorithm.MD5))
            .add(Arguments.of(40, Algorithm.SHA1))
            .add(Arguments.of(56, Algorithm.SHA224))
            .add(Arguments.of(64, Algorithm.SHA256))
            .add(Arguments.of(96, Algorithm.SHA384))
            .add(Arguments.of(128, Algorithm.SHA512))
            .build();
    }

    public static Stream<Arguments> getNameTests() {
        return Stream
            .<Arguments>builder()
            .add(Arguments.of(null, null))
            .add(Arguments.of("", null))
            .add(Arguments.of("md5", Algorithm.MD5))
            .add(Arguments.of("sha1", Algorithm.SHA1))
            .add(Arguments.of("sha224", Algorithm.SHA224))
            .add(Arguments.of("sha256", Algorithm.SHA256))
            .add(Arguments.of("sha384", Algorithm.SHA384))
            .add(Arguments.of("sha512", Algorithm.SHA512))
            .build();
    }

    @ParameterizedTest
    @MethodSource(value = "getLengthTests")
    void find(int input, Algorithm output) {
        assertEquals(output, finder.find(input));
    }

    @ParameterizedTest
    @MethodSource(value = "getNameTests")
    void find(String input, Algorithm output) {
        assertEquals(output, finder.find(input));
    }
}
