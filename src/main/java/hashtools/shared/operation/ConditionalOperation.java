package hashtools.shared.operation;

import hashtools.shared.condition.Condition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class ConditionalOperation {
    private final Condition condition;
    private final Operation operation;
}
