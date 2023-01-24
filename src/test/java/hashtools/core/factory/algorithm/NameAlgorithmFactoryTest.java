package hashtools.core.factory.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameAlgorithmFactoryTest {

    private AlgorithmFactory factory;

    public static List<Arguments> getNameTests() {
        return List.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of("md5", "MD5"),
                Arguments.of("md-5", "MD5"),
                Arguments.of("md--5", "MD5"),
                Arguments.of("sha1", "SHA-1"),
                Arguments.of("sha-1", "SHA-1"),
                Arguments.of("sha--1", "SHA-1"),
                Arguments.of("sha224", "SHA-224"),
                Arguments.of("sha-224", "SHA-224"),
                Arguments.of("sha--224", "SHA-224"),
                Arguments.of("sha256", "SHA-256"),
                Arguments.of("sha-256", "SHA-256"),
                Arguments.of("sha--256", "SHA-256"),
                Arguments.of("sha384", "SHA-384"),
                Arguments.of("sha-384", "SHA-384"),
                Arguments.of("sha--384", "SHA-384"),
                Arguments.of("sha512", "SHA-512"),
                Arguments.of("sha-512", "SHA-512"),
                Arguments.of("sha--512", "SHA-512")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getNameTests")
    @DisplayName(value = "Gets the algorithm that have the given name")
    void create(String name, String expected) {
        factory = new NameAlgorithmFactory(name);

        assertEquals(expected, factory.create());
    }
}
