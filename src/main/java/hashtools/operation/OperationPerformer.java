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


    public static void performAsync(ExecutorService threadPool, Operation operation) {
        performAsync(threadPool, NO_CONDITION, operation, NO_OPERATION);
    }

    public static void performAsync(ExecutorService threadPool, Condition condition, Operation operation) {
        performAsync(threadPool, List.of(condition), operation, NO_OPERATION);
    }

    public static void performAsync(ExecutorService threadPool, Collection<Condition> conditions, Operation operationIfAllTrue) {
        performAsync(threadPool, conditions, operationIfAllTrue, NO_OPERATION);
    }

    public static void performAsync(ExecutorService threadPool, Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        performAsync(threadPool, List.of(condition), operationIfTrue, operationIfFalse);
    }

    public static void performAsync(ExecutorService threadPool, Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        try {
            threadPool.execute(Condition.allOf(conditions).isTrue()
                ? operationIfAllTrue::perform
                : operationIfSomeFalse::perform
            );
        } catch (NullPointerException e) {
            throw new UnsupportedOperationException("Thread pool not provided", e);
        } catch (Exception e) {
            throw new RuntimeException("Unknown issue", e);
        }
    }


    @Deprecated(forRemoval = true)
    public void performAsync(Operation operation) {
        performAsync(NO_CONDITION, operation, NO_OPERATION);
    }

    @Deprecated(forRemoval = true)
    public void performAsync(Condition condition, Operation operation) {
        performAsync(List.of(condition), operation, NO_OPERATION);
    }

    @Deprecated(forRemoval = true)
    public void performAsync(Collection<Condition> conditions, Operation operationIfAllTrue) {
        performAsync(conditions, operationIfAllTrue, NO_OPERATION);
    }

    @Deprecated(forRemoval = true)
    public void performAsync(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        performAsync(List.of(condition), operationIfTrue, operationIfFalse);
    }

    @Deprecated(forRemoval = true)
    public void performAsync(Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        try {
            threadPool.execute(Condition.allOf(conditions).isTrue()
                ? operationIfAllTrue::perform
                : operationIfSomeFalse::perform
            );
        } catch (NullPointerException e) {
            throw new UnsupportedOperationException("Thread pool not provided", e);
        } catch (Exception e) {
            throw new RuntimeException("Unknown issue", e);
        }
    }
}
