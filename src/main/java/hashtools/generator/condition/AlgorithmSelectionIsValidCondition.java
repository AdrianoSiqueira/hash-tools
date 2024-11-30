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
        return !algorithms.isEmpty();
    }
}
