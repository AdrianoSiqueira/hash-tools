package hashtools.generator.condition;

import hashtools.shared.Algorithm;
import hashtools.shared.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AlgorithmSelectionIsValidCondition extends Condition {

    private final List<Algorithm> algorithms;

    @Override
    public boolean isTrue() {
        if (algorithms == null) {
            return false;
        }

        try {
            // Null elements may be allowed, so if list contains it, return false.
            return !algorithms.contains(null);
        } catch (NullPointerException ignored) {
            // Null elements is not allowed, so the list does not contain it.
            return true;
        }
    }
}
