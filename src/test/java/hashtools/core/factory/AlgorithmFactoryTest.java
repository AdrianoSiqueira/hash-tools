package hashtools.core.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AlgorithmFactoryTest {

    private AlgorithmFactory factory = new AlgorithmFactory();

    private static List<Arguments> getExceptionNameTests() {
        return List.of(
                Arguments.of(null, IllegalArgumentException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of("not found", IllegalArgumentException.class)
        );
    }

    private static List<Arguments> getExceptionSizeTests() {
        return List.of(
                Arguments.of(-1, IllegalArgumentException.class),
                Arguments.of(0, IllegalArgumentException.class),
                Arguments.of(1, IllegalArgumentException.class)
        );
    }

    private static List<Arguments> getNameTests() {
        return List.of(
                Arguments.of("md5", "MD5"),
                Arguments.of("sha1", "SHA-1"),
                Arguments.of("sha224", "SHA-224"),
                Arguments.of("sha256", "SHA-256"),
                Arguments.of("sha384", "SHA-384"),
                Arguments.of("sha512", "SHA-512")
        );
    }

    private static List<Arguments> getSizeTests() {
        return List.of(
                Arguments.of(32, "MD5"),
                Arguments.of(40, "SHA-1"),
                Arguments.of(56, "SHA-224"),
                Arguments.of(64, "SHA-256"),
                Arguments.of(96, "SHA-384"),
                Arguments.of(128, "SHA-512")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getNameTests")
    @DisplayName(value = "Determines the algorithm from its name")
    void getAlgorithm(String name, String expected) {
        assertEquals(expected, factory.getAlgorithm(name));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionNameTests")
    @DisplayName(value = "Throws IllegalAccessException when name is not found")
    void getAlgorithm(String name, Class<? extends Throwable> exception) {
        assertThrows(exception, () -> factory.getAlgorithm(name));
    }

    @ParameterizedTest
    @MethodSource(value = "getSizeTests")
    @DisplayName(value = "Determines the algorithm from its size")
    void getAlgorithm(int size, String expected) {
        assertEquals(expected, factory.getAlgorithm(size));
    }

    @ParameterizedTest
    @MethodSource(value = "getExceptionSizeTests")
    @DisplayName(value = "Throws IllegalAccessException when size is not found")
    void getAlgorithm(int size, Class<? extends Throwable> exception) {
        assertThrows(exception, () -> factory.getAlgorithm(size));
    }
}
