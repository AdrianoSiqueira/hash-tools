package hashtools.controller;

import hashtools.condition.Condition;
import hashtools.condition.NoCondition;
import hashtools.domain.Operation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * Intended to be used in UIs with transition (with or
 * without effects) between screens.
 * </p>
 */
public abstract class TransitionedScreenController extends Controller {

    protected ConditionalAction
        btnBackAction,
        btnNextAction;


    @FXML
    protected void btnBackAction(ActionEvent event) {
        threadPool.execute(() -> performOperation(
            btnBackAction.getCondition(),
            btnBackAction.getOperation()
        ));
    }

    @FXML
    protected void btnNextAction(ActionEvent event) {
        threadPool.execute(() -> performOperation(
            btnNextAction.getCondition(),
            btnNextAction.getOperation()
        ));
    }


    /**
     * <p>
     * Holds an operation and a condition to perform that
     * operation. If the operation does not requires a
     * condition, it will use a {@link NoCondition} condition.
     * </p>
     */
    @AllArgsConstructor
    @Getter
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class ConditionalAction {

        private Condition condition;
        private Operation operation;

        /**
         * <p>
         * Creates an action with an operation that does not
         * requires a condition to perform.
         * </p>
         */
        public ConditionalAction(Operation operation) {
            this(new NoCondition(), operation);
        }
    }
}
