package hashtools.shared.condition;

import javafx.scene.input.MouseButton;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MouseButtonIsPrimaryConditionTest {

    private static MouseButton
        nullMouseButton,
        nonPrimaryMouseButton,
        primaryMouseButton;


    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(true, nullMouseButton),
            Arguments.of(true, nonPrimaryMouseButton),
            Arguments.of(false, primaryMouseButton)
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(false, nullMouseButton),
            Arguments.of(false, nonPrimaryMouseButton),
            Arguments.of(true, primaryMouseButton)
        );
    }

    @BeforeAll
    static void setup() {
        nullMouseButton = null;
        nonPrimaryMouseButton = MouseButton.SECONDARY;
        primaryMouseButton = MouseButton.PRIMARY;
    }


    @ParameterizedTest
    @MethodSource(value = "getFalseTests")
    void isFalse(boolean expectedResult, MouseButton mouseButton) {
        assertEquals(
            expectedResult,
            new MouseButtonIsPrimaryCondition(mouseButton).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTrueTests")
    void isTrue(boolean expectedResult, MouseButton mouseButton) {
        assertEquals(
            expectedResult,
            new MouseButtonIsPrimaryCondition(mouseButton).isTrue()
        );
    }
}
