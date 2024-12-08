package hashtools.generator.condition;

import hashtools.shared.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmSelectionIsValidConditionTest {

    @Test
    void isFalseReturnsFalseWhenListIsValid() {
        assertFalse(
            new AlgorithmSelectionIsValidCondition(List.of(Algorithm.MD5)).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenListIsEmpty() {
        assertTrue(
            new AlgorithmSelectionIsValidCondition(List.of()).isFalse()
        );
    }

    @Test
    void isFalseReturnsTrueWhenListIsNull() {
        assertTrue(
            new AlgorithmSelectionIsValidCondition(null).isFalse()
        );
    }

    @Test
    void isTrueReturnsFalseWhenListIsEmpty() {
        assertFalse(
            new AlgorithmSelectionIsValidCondition(List.of()).isTrue()
        );
    }

    @Test
    void isTrueReturnsFalseWhenListIsNull() {
        assertFalse(
            new AlgorithmSelectionIsValidCondition(null).isTrue()
        );
    }

    @Test
    void isTrueReturnsTrueWhenListIsValid() {
        assertTrue(
            new AlgorithmSelectionIsValidCondition(List.of(Algorithm.MD5)).isTrue()
        );
    }
}
