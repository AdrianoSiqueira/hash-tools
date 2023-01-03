package hashtools.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmServiceTest {

    private AlgorithmService service = new AlgorithmService();


    private static List<Arguments> getAlgorithmTests() {
        return List.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("MD5", true),
                Arguments.of("SHA-1", true),
                Arguments.of("SHA-224", true),
                Arguments.of("SHA-256", true),
                Arguments.of("SHA-384", true),
                Arguments.of("SHA-512", true)
        );
    }


    @ParameterizedTest
    @MethodSource(value = "getAlgorithmTests")
    @DisplayName(value = "Determines if the algorithm is valid")
    void algorithmIsValid(String algorithm, boolean expected) {
        assertEquals(expected, service.algorithmIsValid(algorithm));
    }
}
