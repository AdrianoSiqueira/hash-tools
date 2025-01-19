package hashtools.applicationmodule.generator.condition;

import hashtools.coremodule.checksumgenerator.Algorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmSelectionIsValidConditionTest {

    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(false, List.of(Algorithm.MD5)),
            Arguments.of(false, List.of(Algorithm.MD5, Algorithm.SHA1)),
            Arguments.of(false, List.of()),
            Arguments.of(true, null),
            Arguments.of(true, Collections.singletonList(null)),
            Arguments.of(true, Arrays.asList(Algorithm.MD5, null))
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(true, List.of(Algorithm.MD5)),
            Arguments.of(true, List.of(Algorithm.MD5, Algorithm.SHA1)),
            Arguments.of(true, List.of()),
            Arguments.of(false, null),
            Arguments.of(false, Collections.singletonList(null)),
            Arguments.of(false, Arrays.asList(Algorithm.MD5, null))
        );
    }

    @ParameterizedTest
    @MethodSource("getFalseTests")
    void isFalse(boolean expected, List<Algorithm> algorithms) {
        assertEquals(
            expected,
            new AlgorithmSelectionIsValidCondition(algorithms).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource("getTrueTests")
    void isTrue(boolean expected, List<Algorithm> algorithms) {
        assertEquals(
            expected,
            new AlgorithmSelectionIsValidCondition(algorithms).isTrue()
        );
    }
}
