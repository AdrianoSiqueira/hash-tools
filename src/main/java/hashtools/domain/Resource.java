package hashtools.domain;

import javafx.event.ActionEvent;

import java.util.concurrent.ExecutorService;

public final class Resource {

    public static final class PseudoClass {
        public static final javafx.css.PseudoClass ARMED = javafx.css.PseudoClass.getPseudoClass("armed");
        public static final javafx.css.PseudoClass DISABLED = javafx.css.PseudoClass.getPseudoClass("disabled");
    }

    public static final class EventHandler {
        public static final javafx.event.EventHandler<ActionEvent> NO_ACTION_EVENT = _ -> {};
    }

    public static final class FXMLPath {
        public static final String CHECKER_SCREEN = "/hashtools/fxml/checker-screen.fxml";
        public static final String COMPARATOR_SCREEN = "/hashtools/fxml/checker-screen.fxml";
        public static final String GENERATOR_SCREEN = "/hashtools/fxml/checker-screen.fxml";
    }

    public static final class Hardware {
        public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = ThreadPool.newCachedDaemon();
    }
}
