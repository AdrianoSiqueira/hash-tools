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

    private static final PseudoClass ARMED = PseudoClass.getPseudoClass("armed");

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
