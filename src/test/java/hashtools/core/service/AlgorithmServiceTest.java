package hashtools.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * <p style="text-align:justify">
 * Tests the {@link AlgorithmService} class.
 * </p>
 */
class AlgorithmServiceTest {

    private AlgorithmService service = new AlgorithmService();

    private static Stream<Arguments> getLengthTests() {
        return Stream.of(
                Arguments.of(0, null, null),
                Arguments.of(32, "MD5", null),
                Arguments.of(40, "SHA-1", null),
                Arguments.of(56, "SHA-224", null),
                Arguments.of(64, "SHA-256", null),
                Arguments.of(96, "SHA-384", null),
                Arguments.of(128, "SHA-512", null)
        );
    }

    private static Stream<Arguments> getNameTests() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of("", null, null),
                Arguments.of("md5", "MD5", null),
                Arguments.of("md--5", "MD5", null),
                Arguments.of("sha1", "SHA-1", null),
                Arguments.of("sha--1", "SHA-1", null),
                Arguments.of("sha224", "SHA-224", null),
                Arguments.of("sha--224", "SHA-224", null),
                Arguments.of("sha256", "SHA-256", null),
                Arguments.of("sha--256", "SHA-256", null),
                Arguments.of("sha384", "SHA-384", null),
                Arguments.of("sha--384", "SHA-384", null),
                Arguments.of("sha512", "SHA-512", null),
                Arguments.of("sha--512", "SHA-512", null)
        );
    }

    @ParameterizedTest
    @DisplayName(value = "Determine the algorithm from its length")
    @MethodSource(value = "getLengthTests")
    void getAlgorithm(int length, String algorithm, Class<? extends Throwable> exception) {
        if (exception != null) {
            assertThrows(exception, () -> service.getAlgorithm(length));
        } else {
            assertEquals(algorithm, service.getAlgorithm(length));
        }
    }

    @ParameterizedTest
    @DisplayName(value = "Determine the algorithm from its name")
    @MethodSource(value = "getNameTests")
    void getAlgorithm(String name, String algorithm, Class<? extends Throwable> exception) {
        if (exception != null) {
            assertThrows(exception, () -> service.getAlgorithm(name));
        } else {
            assertEquals(algorithm, service.getAlgorithm(name));
        }
    }
}
