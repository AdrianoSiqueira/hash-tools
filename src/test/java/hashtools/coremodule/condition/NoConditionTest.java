package hashtools.coremodule.condition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoConditionTest {

    @Test
    void isFalseAlwaysReturnsFalse() {
        assertFalse(
            () -> new NoCondition().isFalse()
        );
    }

    @Test
    void isTrueAlwaysReturnsTrue() {
        assertTrue(
            () -> new NoCondition().isTrue()
        );
    }
}
