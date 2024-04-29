package hashtools.utility;

import hashtools.domain.algorithm.Algorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AlgorithmFinderTest {

    @ParameterizedTest
    @MethodSource(value = "hashtools.utility.AlgorithmFinderTest$TestFactory#getLengthResultTests")
    void find(int input, Algorithm output) {
        assertThat(Algorithm.get(input))
            .isPresent()
            .contains(output);
    }

    @ParameterizedTest
    @MethodSource(value = "hashtools.utility.AlgorithmFinderTest$TestFactory#getLengthErrorTests")
    void find(int input) {
        assertThat(Algorithm.get(input))
            .isEmpty();
    }

    @ParameterizedTest
    @MethodSource(value = "hashtools.utility.AlgorithmFinderTest$TestFactory#getNameResultTests")
    void find(String input, Algorithm output) {
        assertThat(Algorithm.get(input))
            .isPresent()
            .contains(output);
    }

    @ParameterizedTest
    @MethodSource(value = "hashtools.utility.AlgorithmFinderTest$TestFactory#getNameErrorTests")
    void find(String input) {
        assertThat(Algorithm.get(input))
            .isEmpty();
    }


    private static class TestFactory {

        public static Stream<Arguments> getLengthErrorTests() {
            return Stream
                .<Arguments>builder()
                .add(Arguments.of(0))
                .build();
        }

        public static Stream<Arguments> getLengthResultTests() {
            return Stream
                .<Arguments>builder()
                .add(Arguments.of(32, Algorithm.MD5))
                .add(Arguments.of(40, Algorithm.SHA1))
                .add(Arguments.of(56, Algorithm.SHA224))
                .add(Arguments.of(64, Algorithm.SHA256))
                .add(Arguments.of(96, Algorithm.SHA384))
                .add(Arguments.of(128, Algorithm.SHA512))
                .build();
        }

        public static Stream<Arguments> getNameErrorTests() {
            return Stream
                .<Arguments>builder()
                .add(Arguments.of(""))
                .add(Arguments.of((Object) null))
                .build();
        }

        public static Stream<Arguments> getNameResultTests() {
            return Stream
                .<Arguments>builder()
                .add(Arguments.of("md5", Algorithm.MD5))
                .add(Arguments.of("sha1", Algorithm.SHA1))
                .add(Arguments.of("sha224", Algorithm.SHA224))
                .add(Arguments.of("sha256", Algorithm.SHA256))
                .add(Arguments.of("sha384", Algorithm.SHA384))
                .add(Arguments.of("sha512", Algorithm.SHA512))
                .build();
        }
    }
}
