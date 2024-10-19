package hashtools.domain;

import hashtools.condition.Condition;
import hashtools.condition.NoCondition;
import hashtools.operation.NoOperation;
import hashtools.operation.Operation;

import java.util.concurrent.ExecutorService;

public final class Resource {

    public static final class ConditionalOperation {
        public static final hashtools.operation.ConditionalOperation NO_ACTION = new hashtools.operation.ConditionalOperation(StaticImplementation.NO_CONDITION, StaticImplementation.NO_OPERATION);
    }

    public static final class FXMLPath {
        public static final String CHECKER_SCREEN = "/hashtools/fxml/checker-screen.fxml";
        public static final String COMPARATOR_SCREEN = "/hashtools/fxml/comparator-screen.fxml";
        public static final String GENERATOR_SCREEN = "/hashtools/fxml/generator-screen.fxml";
    }

    public static final class Hardware {
        public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static final class PseudoClass {
        public static final javafx.css.PseudoClass ARMED = javafx.css.PseudoClass.getPseudoClass("armed");
        public static final javafx.css.PseudoClass DISABLED = javafx.css.PseudoClass.getPseudoClass("disabled");
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = ThreadPool.newCachedDaemon();
    }

    public static final class StaticImplementation {
        public static final Condition NO_CONDITION = new NoCondition();
        public static final Operation NO_OPERATION = new NoOperation();
    }
}
