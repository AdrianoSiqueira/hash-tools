package hashtools.controller;

import hashtools.condition.Condition;
import hashtools.domain.Operation;
import hashtools.domain.ThreadPool;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public abstract class Controller implements Initializable {

    protected static final PseudoClass
        ARMED = PseudoClass.getPseudoClass("armed"),
        DISABLED = PseudoClass.getPseudoClass("disabled");

    /**
     * <p>
     * Internal thread pool. This pool do not need to be
     * closed because is uses daemon threads.
     * </p>
     */
    protected final ExecutorService threadPool = ThreadPool.newCachedDaemon();


    /**
     * <p>
     * Performs the operation.
     * </p>
     *
     * @param operation Operation to perform.
     */
    protected final void performOperation(Operation operation) {
        operation.perform();
    }

    /**
     * <p>
     * Performs the operation if the condition is true.
     * </p>
     *
     * @param condition Determines if the operation should be performed.
     * @param operation Operation to perform.
     */
    protected final void performOperation(Condition condition, Operation operation) {
        if (condition.isTrue()) {
            operation.perform();
        }
    }

    /**
     * <p>
     * Performs the conditions if all conditions are true.
     * </p>
     *
     * @param conditions Determines the operation that will perform.
     * @param operation  Operation to perform if all conditions are true.
     */
    protected final void performOperation(Collection<Condition> conditions, Operation operation) {
        boolean allConditionsAreTrue = conditions
            .stream()
            .allMatch(Condition::isTrue);

        if (allConditionsAreTrue) {
            operation.perform();
        }
    }

    /**
     * <p>
     * Performs one of the operation based in the condition.
     * </p>
     *
     * @param condition        Determines the operation that will perform.
     * @param operationIfTrue  Operation to perform if condition is true.
     * @param operationIfFalse Operation to perform if condition is false.
     */
    protected final void performOperation(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        if (condition.isTrue()) {
            operationIfTrue.perform();
        } else {
            operationIfFalse.perform();
        }
    }

    /**
     * <p>
     * Performs one of the conditions based in all conditions.
     * </p>
     *
     * @param conditions           Determines the operation that will perform.
     * @param operationIfAllTrue   Operation to perform if all conditions are true.
     * @param operationIfSomeFalse Operation to perform if some condition is false.
     */
    protected final void performOperation(Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        boolean allConditionsAreTrue = conditions
            .stream()
            .allMatch(Condition::isTrue);

        if (allConditionsAreTrue) {
            operationIfAllTrue.perform();
        } else {
            operationIfSomeFalse.perform();
        }
    }

    /**
     * <p>
     * Resets the UI state clearing text fields, applying
     * default selection to checkboxes and so on.
     * </p>
     */
    protected abstract void resetUI();

    /**
     * <p>
     * Returns an {@link  EventHandler} that performs the operation.
     * </p>
     *
     * @param operation Operation to perform.
     * @param <T>       The type of the {@link  EventHandler}.
     *
     * @return An {@link EventHandler} that performs the operation.
     */
    protected final <T extends Event> EventHandler<T> triggerOperation(Operation operation) {
        return _ -> performOperation(operation);
    }

    /**
     * <p>
     * Returns an {@link  EventHandler} that performs the operation if the condition is true.
     * </p>
     *
     * @param condition Determines if the operation should be performed.
     * @param operation Operation to perform.
     * @param <T>The    type of the {@link  EventHandler}.
     *
     * @return An {@link EventHandler} that performs the operation.
     */
    protected final <T extends Event> EventHandler<T> triggerOperation(Condition condition, Operation operation) {
        return _ -> performOperation(condition, operation);
    }


    /**
     * <p>
     * Sets the armed state of the node to true.
     * </p>
     */
    @RequiredArgsConstructor
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class ArmNode implements Operation {

        private final Node node;

        @Override
        public void perform() {
            node.pseudoClassStateChanged(ARMED, true);
        }
    }

    /**
     * <p>
     * Sets the armed state of the node to false.
     * </p>
     */
    @RequiredArgsConstructor
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class DisarmNode implements Operation {

        private final Node node;

        @Override
        public void perform() {
            node.pseudoClassStateChanged(ARMED, false);
        }
    }

    /**
     * <p>
     * Throws the given exception.
     * </p>
     */
    @RequiredArgsConstructor
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class ThrowException implements Operation {

        private final RuntimeException exception;

        @Override
        public void perform() {
            throw exception;
        }
    }
}
