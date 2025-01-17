package hashtools.coremodule.condition;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyboardKeyIsActionKeyConditionTest {

    private static KeyCode
        nullKeyCode,
        noActionKeyCode,
        actionKeyCode;


    static Stream<Arguments> getFalseTests() {
        return Stream.of(
            Arguments.of(true, nullKeyCode),
            Arguments.of(true, noActionKeyCode),
            Arguments.of(false, actionKeyCode)
        );
    }

    static Stream<Arguments> getTrueTests() {
        return Stream.of(
            Arguments.of(false, nullKeyCode),
            Arguments.of(false, noActionKeyCode),
            Arguments.of(true, actionKeyCode)
        );
    }

    @BeforeAll
    static void setup() {
        nullKeyCode = null;
        noActionKeyCode = javafx.scene.input.KeyCode.A;
        actionKeyCode = javafx.scene.input.KeyCode.ENTER;
    }


    @ParameterizedTest
    @MethodSource(value = "getFalseTests")
    void isFalse(boolean expectedResult, KeyCode keyCode) {
        assertEquals(
            expectedResult,
            new KeyboardKeyIsActionKeyCondition(keyCode).isFalse()
        );
    }

    @ParameterizedTest
    @MethodSource(value = "getTrueTests")
    void isTrue(boolean expectedResult, KeyCode keyCode) {
        assertEquals(
            expectedResult,
            new KeyboardKeyIsActionKeyCondition(keyCode).isTrue()
        );
    }
}
