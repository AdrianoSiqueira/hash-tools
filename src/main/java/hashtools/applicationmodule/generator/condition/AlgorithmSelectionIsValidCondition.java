package hashtools.applicationmodule.generator.condition;

import hashtools.coremodule.Algorithm;
import hashtools.coremodule.condition.Condition;
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
             * If list implementation allows null items we
             * check if there is any. If some null item is
             * present we return false because in business
             * logic it is not allowed.
             *
             * If list implementation does not allow null
             * items the checking will throw an exception,
             * in this case we return true because there
             * is no null item, making the list valid.
             */
            boolean nullIsPresent = algorithms.contains(null);
            return !nullIsPresent;
        } catch (NullPointerException ignored) {
            return true;
        }
    }
}
