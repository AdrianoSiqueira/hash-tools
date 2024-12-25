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
            /*
             * List implementation may allow null items, so
             * if it is present return false because in business
             * logic null items are not allowed.
             */
            return !algorithms.contains(null);
        } catch (NullPointerException ignored) {
            /*
             * List implementation does not allow null items,
             * in this case return true because all other
             * items are allowed in business logic.
             */
            return true;
        }
    }
}
