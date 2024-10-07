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
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

@Slf4j
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
     * Performs the operation if all conditions are true.
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
     * Performs one of the operation based in all conditions.
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
     * Performs the operation in the thread pool.
     * </p>
     *
     * @param operation Operation to perform.
     */
    protected final void performOperationAsync(Operation operation) {
        threadPool.execute(operation::perform);
    }

    /**
     * <p>
     * Performs the operation in the thread pool if the
     * condition is true.
     * </p>
     *
     * @param condition Determines if the operation should be performed.
     * @param operation Operation to perform.
     */
    protected final void performOperationAsync(Condition condition, Operation operation) {
        if (condition.isTrue()) {
            threadPool.execute(operation::perform);
        }
    }

    /**
     * <p>
     * Performs the operation in the tread pool if all
     * conditions are true.
     * </p>
     *
     * @param conditions Determines the operation that will perform.
     * @param operation  Operation to perform if all conditions are true.
     */
    protected final void performOperationAsync(Collection<Condition> conditions, Operation operation) {
        if (Condition.allOf(conditions).isTrue()) {
            threadPool.execute(operation::perform);
        }
    }

    /**
     * <p>
     * Performs one of the operation in the thread pool
     * based in the condition.
     * </p>
     *
     * @param condition        Determines the operation that will perform.
     * @param operationIfTrue  Operation to perform if condition is true.
     * @param operationIfFalse Operation to perform if condition is false.
     */
    protected final void performOperationAsync(Condition condition, Operation operationIfTrue, Operation operationIfFalse) {
        threadPool.execute(condition.isTrue()
            ? operationIfTrue::perform
            : operationIfFalse::perform
        );
    }

    /**
     * <p>
     * Performs one of the operation in the thread pool
     * based in all conditions.
     * </p>
     *
     * @param conditions           Determines the operation that will perform.
     * @param operationIfAllTrue   Operation to perform if all conditions are true.
     * @param operationIfSomeFalse Operation to perform if some condition is false.
     */
    protected final void performOperationAsync(Collection<Condition> conditions, Operation operationIfAllTrue, Operation operationIfSomeFalse) {
        threadPool.execute(
            Condition.allOf(conditions).isTrue()
                ? operationIfAllTrue::perform
                : operationIfSomeFalse::perform
        );
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
     * Gets translation for the given dictionary entry. If
     * some issue occurs, the entry itself will be returned.
     * </p>
     *
     * @param dictionary Dictionary used to get translation.
     * @param entry      Dictionary entry to get translation.
     *
     * @return The translation for the given entry, or the
     * entry itself if translation fails.
     */
    protected final String translate(ResourceBundle dictionary, String entry) {
        try {
            return dictionary.getString(entry);
        } catch (NullPointerException e) {
            log.error("The dictionary or entry is null", e);
        } catch (MissingResourceException e) {
            log.error("The entry was not found in dictionary", e);
        } catch (Exception e) {
            log.error("Unknown issue", e);
        }

        return entry;
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
