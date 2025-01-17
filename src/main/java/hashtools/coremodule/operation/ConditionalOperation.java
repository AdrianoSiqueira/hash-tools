package hashtools.coremodule.operation;

import hashtools.coremodule.condition.Condition;
import lombok.AllArgsConstructor;

import static hashtools.coremodule.Resource.StaticImplementation.NO_CONDITION;
import static hashtools.coremodule.Resource.StaticImplementation.NO_OPERATION;

@AllArgsConstructor
public class ConditionalOperation extends Operation {

    private final Condition condition;
    private final Operation operationIfTrue;
    private final Operation operationIfFalse;

    public ConditionalOperation(Operation operation) {
        this(
            NO_CONDITION,
            operation,
            NO_OPERATION
        );
    }

    public ConditionalOperation(Condition condition, Operation operationIfTrue) {
        this(
            condition,
            operationIfTrue,
            NO_OPERATION
        );
    }

    @Override
    protected void perform() {
        if (condition.isTrue()) {
            operationIfTrue.perform();
        } else {
            operationIfFalse.perform();
        }
    }
}
