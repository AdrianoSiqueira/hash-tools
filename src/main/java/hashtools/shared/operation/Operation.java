package hashtools.shared.operation;

import java.util.concurrent.Executor;

public abstract class Operation {

    public static void perform(Operation... operations) {
        for (Operation operation : operations) {
            operation.perform();
        }
    }

    public static void perform(Executor threadPool, Operation... operations) {
        for (Operation operation : operations) {
            threadPool.execute(operation::perform);
        }
    }


    protected abstract void perform();
}
