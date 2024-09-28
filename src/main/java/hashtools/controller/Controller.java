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

import java.util.concurrent.ExecutorService;

public abstract class Controller implements Initializable {

    private static final PseudoClass ARMED = PseudoClass.getPseudoClass("armed");

    protected final ExecutorService threadPool = ThreadPool.newCachedDaemon();


    protected final void performOperation(Operation operation) {
        operation.perform();
    }

    protected final void performOperation(Condition condition, Operation operation) {
        if (condition.isTrue()) {
            operation.perform();
        }
    }

    protected final <T extends Event> EventHandler<T> triggerOperation(Operation operation) {
        return _ -> performOperation(operation);
    }

    protected final <T extends Event> EventHandler<T> triggerOperation(Condition condition, Operation operation) {
        return _ -> performOperation(condition, operation);
    }


    @RequiredArgsConstructor
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class ArmNode implements Operation {

        private final Node node;

        @Override
        public void perform() {
            node.pseudoClassStateChanged(ARMED, true);
        }
    }

    @RequiredArgsConstructor
    @SuppressWarnings("InnerClassMayBeStatic")
    protected final class DisarmNode implements Operation {

        private final Node node;

        @Override
        public void perform() {
            node.pseudoClassStateChanged(ARMED, false);
        }
    }

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
