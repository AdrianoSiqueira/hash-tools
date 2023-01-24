package hashtools.core.factory.algorithm;

import javafx.fxml.FXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LengthAlgorithmFactoryTest {

    private AlgorithmFactory factory;

    public static List<Arguments> getLengthTests() {
        return List.of(
                Arguments.of(-1, null),
                Arguments.of(0, null),
                Arguments.of(32, "MD5"),
                Arguments.of(40, "SHA-1"),
                Arguments.of(56, "SHA-224"),
                Arguments.of(64, "SHA-256"),
                Arguments.of(96, "SHA-384"),
                Arguments.of(128, "SHA-512")
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getLengthTests")
    @DisplayName(value = "Gets the algorithm that have the given length")
    void create(int length, String expected) {
        factory = new LengthAlgorithmFactory(length);

        assertEquals(expected, factory.create());
    }
}
