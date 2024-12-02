package hashtools.shared.condition;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MouseButtonIsPrimaryConditionTest {

    private static MouseEvent nullButton;
    private static MouseEvent primaryButton;
    private static MouseEvent secondaryButton;


    @BeforeAll
    static void createButtons() {
        nullButton = null;
        primaryButton = new MouseEvent(null, 0, 0, 0, 0, MouseButton.PRIMARY, 0, false, false, false, false, false, false, false, false, false, false, null);
        secondaryButton = new MouseEvent(null, 0, 0, 0, 0, MouseButton.SECONDARY, 0, false, false, false, false, false, false, false, false, false, false, null);
    }


    @Test
    void isFalseReturnsFalseWhenButtonIsPrimary() {
        assertFalse(
            () -> new MouseButtonIsPrimaryCondition(primaryButton).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenButtonIsNotPrimary() {
        assertTrue(
            () -> new MouseButtonIsPrimaryCondition(secondaryButton).isFalse()
        );
    }

    @Test
    void isFalseThrowsNullPointerExceptionWhenButtonIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new MouseButtonIsPrimaryCondition(nullButton).isFalse()
        );
    }


    @Test
    void isTrueReturnsFalseWhenButtonIsNotPrimary() {
        assertFalse(
            () -> new MouseButtonIsPrimaryCondition(secondaryButton).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenButtonIsPrimary() {
        assertTrue(
            () -> new MouseButtonIsPrimaryCondition(primaryButton).isTrue()
        );
    }

    @Test
    void isTrueThrowsNullPointerExceptionWhenButtonIsNull() {
        assertThrows(
            NullPointerException.class,
            () -> new MouseButtonIsPrimaryCondition(nullButton).isTrue()
        );
    }
}
