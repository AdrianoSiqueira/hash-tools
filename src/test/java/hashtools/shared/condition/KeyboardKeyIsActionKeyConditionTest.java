package hashtools.shared.condition;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyboardKeyIsActionKeyConditionTest {

    private static KeyEvent nullKey;
    private static KeyEvent actionKey;
    private static KeyEvent noActionKey;


    @BeforeAll
    static void createKeys() {
        nullKey = null;
        actionKey = new KeyEvent(null, null, null, KeyCode.ENTER, false, false, false, false);
        noActionKey = new KeyEvent(null, null, null, KeyCode.ESCAPE, false, false, false, false);
    }


    @Test
    void isFalseReturnsFalseWhenKeyCodeIsActionKey() {
        assertFalse(
            () -> new KeyboardKeyIsActionKeyCondition(actionKey).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenKeyCodeIsNotActionKey() {
        assertTrue(
            () -> new KeyboardKeyIsActionKeyCondition(noActionKey).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenKeyEventIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new KeyboardKeyIsActionKeyCondition(nullKey).isFalse()
        );
    }


    @Test
    void isTrueReturnsFalseWhenKeyCodeIsNotActionKey() {
        assertFalse(
            () -> new KeyboardKeyIsActionKeyCondition(noActionKey).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenKeyCodeIsActionKey() {
        assertTrue(
            () -> new KeyboardKeyIsActionKeyCondition(actionKey).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenKeyEventIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new KeyboardKeyIsActionKeyCondition(nullKey).isTrue()
        );
    }
}
