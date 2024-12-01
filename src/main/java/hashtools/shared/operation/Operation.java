package hashtools.shared.operation;

import java.util.concurrent.Executor;

public abstract class Operation {

    public static void perform(Operation operation) {
        operation.perform();
    }

    public static void perform(Operation operation, Executor threadPool) {
        threadPool.execute(operation::perform);
    }

    protected abstract void perform();
}
