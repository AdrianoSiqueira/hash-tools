package hashtools.domain;

import javafx.css.PseudoClass;

import java.util.concurrent.ExecutorService;

public final class Resource {

    public static final class Hardware {
        public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = ThreadPool.newCachedDaemon();
    }

    public static final class Static {
        public static final PseudoClass ARMED = PseudoClass.getPseudoClass("armed");
        public static final PseudoClass DISABLED = PseudoClass.getPseudoClass("disabled");

        public static final String CHECKER_SCREEN_PATH = "/hashtools/fxml/checker-screen.fxml";
        public static final String COMPARATOR_SCREEN_PATH = "/hashtools/fxml/checker-screen.fxml";
        public static final String GENERATOR_SCREEN_PATH = "/hashtools/fxml/checker-screen.fxml";
    }
}
