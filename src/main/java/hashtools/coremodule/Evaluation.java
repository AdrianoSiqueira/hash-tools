package hashtools.coremodule;

import hashtools.coremodule.condition.Condition;

public abstract class Evaluation {

    public static void evaluate(Evaluation evaluation) {
        evaluation.evaluate();
    }

    protected final void evaluate(Condition condition, RuntimeException exception) {
        if (condition.isFalse()) {
            throw exception;
        }
    }

    protected abstract void evaluate();
}
