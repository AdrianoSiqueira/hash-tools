package hashtools.operation;

import hashtools.condition.Condition;

import static hashtools.domain.Resource.StaticImplementation.NO_CONDITION;
import static hashtools.domain.Resource.StaticImplementation.NO_OPERATION;

public class OperationPerformer {

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
