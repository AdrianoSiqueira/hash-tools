package hashtools.operation;

import hashtools.condition.Condition;
import hashtools.condition.NoCondition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OperationPerformer {

    private static final Condition NO_CONDITION = new NoCondition();
    private static final Operation NO_OPERATION = new NoOperation();


    public static void perform(Operation operation) {
        perform(NO_CONDITION, operation, NO_OPERATION);
    }

    public static void perform(Condition condition, Operation operation) {
        perform(condition, operation, NO_OPERATION);
    }

    public static void perform(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        if (condition.isTrue()) {
            operationIfTrue.perform();
        } else {
            operationIfFalse.perform();
        }
    }


    public static void performAsync(Operation operation) {
        performAsync(NO_CONDITION, operation, NO_OPERATION);
    }

    public static void performAsync(Condition condition, Operation operation) {
        performAsync(condition, operation, NO_OPERATION);
    }

    public static void performAsync(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        new Thread(
            condition.isTrue()
                ? operationIfTrue::perform
                : operationIfFalse::perform
        ).start();
    }
}
