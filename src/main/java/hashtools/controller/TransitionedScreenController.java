package hashtools.controller;

import hashtools.condition.CollectionContainsItem;
import hashtools.condition.Condition;
import hashtools.condition.NoCondition;
import hashtools.condition.ObjectIsNotNull;
import hashtools.operation.Operation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

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

    protected Collection<Pane> screenPanes;

    @FXML
    protected void btnBackAction(ActionEvent event) {
        operationPerformer.performAsync(
            btnBackAction.getCondition(),
            btnBackAction.getOperation()
        );
    }

    @FXML
    protected void btnNextAction(ActionEvent event) {
        operationPerformer.performAsync(
            btnNextAction.getCondition(),
            btnNextAction.getOperation()
        );
    }

    protected final void showScreenPane(Pane screenPane) {
        List<Condition> conditions = List.of(
            new ObjectIsNotNull(screenPanes),
            new CollectionContainsItem<>(screenPanes, screenPane)
        );

        operationPerformer.perform(
            conditions,
            new ShowScreenPane(screenPane),
            new ThrowException(new IllegalStateException("Panes list is null or screen pane is not present in list"))
        );
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

    @RequiredArgsConstructor
    private final class ShowScreenPane implements Operation {

        private final Pane screenPane;

        @Override
        public void perform() {
            screenPanes.forEach(pane -> pane.setVisible(false));
            screenPane.setVisible(true);
        }
    }
}
