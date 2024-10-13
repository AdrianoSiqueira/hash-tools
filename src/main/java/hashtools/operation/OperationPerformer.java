package hashtools.operation;

import hashtools.condition.Condition;
import hashtools.condition.NoCondition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;

@AllArgsConstructor
@NoArgsConstructor
public class OperationPerformer {

    private static final List<Condition> NO_CONDITION = List.of(new NoCondition());
    private static final Operation NO_OPERATION = new NoOperation();

    @Deprecated(forRemoval = true)
    private ExecutorService threadPool;


    public static void perform(Operation operation) {
        perform(NO_CONDITION, operation, NO_OPERATION);
    }

    public static void perform(Condition condition, Operation operation) {
        perform(List.of(condition), operation, NO_OPERATION);
    }

    public static void perform(Collection<Condition> conditions, Operation operationIfAllTrue) {
        perform(conditions, operationIfAllTrue, NO_OPERATION);
    }

    public static void perform(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        perform(List.of(condition), operationIfTrue, operationIfFalse);
    }

    public static void perform(Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        if (Condition.allOf(conditions).isTrue()) {
            operationIfAllTrue.perform();
        } else {
            operationIfSomeFalse.perform();
        }
    }


    public static void performAsync(Operation operation) {
        performAsync(NO_CONDITION, operation, NO_OPERATION);
    }

    public static void performAsync(Condition condition, Operation operation) {
        performAsync(List.of(condition), operation, NO_OPERATION);
    }

    public static void performAsync(Collection<Condition> conditions, Operation operationIfAllTrue) {
        performAsync(conditions, operationIfAllTrue, NO_OPERATION);
    }

    public static void performAsync(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        performAsync(List.of(condition), operationIfTrue, operationIfFalse);
    }

    /**
     * @deprecated Replace this method with the
     * {@link #performAsync(Condition, Operation, Operation)}
     * one. The client will be responsible to call
     * {@link Condition#allOf(Collection)} method.
     */
    @Deprecated
    public static void performAsync(Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        try {
            new Thread(
                Condition.allOf(conditions).isTrue()
                    ? operationIfAllTrue::perform
                    : operationIfSomeFalse::perform
            ).start();
        } catch (NullPointerException e) {
            throw new UnsupportedOperationException("Some parameter is null", e);
        } catch (Exception e) {
            throw new RuntimeException("Unknown issue", e);
        }
    }
}
