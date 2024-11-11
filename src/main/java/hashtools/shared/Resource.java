package hashtools.shared;

import hashtools.shared.condition.Condition;
import hashtools.shared.condition.NoCondition;
import hashtools.shared.operation.NoOperation;
import hashtools.shared.operation.Operation;
import hashtools.shared.threadpool.ThreadPool;

import java.util.concurrent.ExecutorService;

public final class Resource {

    public static final class ConditionalOperation {
        public static final hashtools.shared.operation.ConditionalOperation NO_ACTION = new hashtools.shared.operation.ConditionalOperation(StaticImplementation.NO_CONDITION, StaticImplementation.NO_OPERATION);
    }

    public static final class FXMLPath {
        public static final String CHECKER_SCREEN = "/hashtools/checker/checker-screen.fxml";
        public static final String COMPARATOR_SCREEN = "/hashtools/comparator/comparator-screen.fxml";
        public static final String GENERATOR_SCREEN = "/hashtools/generator/generator-screen.fxml";
        public static final String APPLICATION_SCREEN = "/hashtools/application/application-screen.fxml";
    }

    public static final class Hardware {
        public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static final class PropertyKey {
        public static final String HOME_DIRECTORY = "user.home";
    }

    public static final class PseudoClass {
        public static final javafx.css.PseudoClass ARMED = javafx.css.PseudoClass.getPseudoClass("armed");
        public static final javafx.css.PseudoClass DISABLED = javafx.css.PseudoClass.getPseudoClass("disabled");
    }

    public static final class ResourceBundle {
        public static final String LANGUAGE = "hashtools.shared.language.language";
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = ThreadPool.newCachedDaemon("GlobalThreadPool");
    }

    public static final class StaticImplementation {
        public static final Condition NO_CONDITION = new NoCondition();
        public static final Operation NO_OPERATION = new NoOperation();
    }
}
