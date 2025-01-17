package hashtools.coremodule;

import hashtools.coremodule.condition.Condition;
import hashtools.coremodule.condition.NoCondition;
import hashtools.coremodule.operation.NoOperation;
import hashtools.coremodule.operation.Operation;
import hashtools.coremodule.threadpool.ThreadPool;
import hashtools.applicationmodule.preloader.PreloaderWindow;

import java.util.concurrent.ExecutorService;

public final class Resource {

    public static final class ApplicationDimension {
        public static final double WIDTH = 853.0;
        public static final double HEIGHT = 480.0;
    }

    public static final class FXMLPath {
        public static final String APPLICATION_SCREEN = "/hashtools/applicationmodule/application/application-screen.fxml";
        public static final String CHECKER_SCREEN = "/hashtools/applicationmodule/checker/checker-screen.fxml";
        public static final String COMPARATOR_SCREEN = "/hashtools/applicationmodule/comparator/comparator-screen.fxml";
        public static final String GENERATOR_SCREEN = "/hashtools/applicationmodule/generator/generator-screen.fxml";
        public static final String PRELOADER_SCREEN = "/hashtools/applicationmodule/preloader/preloader-screen.fxml";
    }

    public static final class Hardware {
        public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
    }

    public static final class ImagePath {
        public static final String FAV_ICON = "/hashtools/coremodule/image/1920x1920/application.png";
    }

    public static final class Language {
        public static final String APPLICATION = "hashtools.application.language.language";
        public static final String CHECKER = "hashtools.checker.language.language";
        public static final String COMPARATOR = "hashtools.comparator.language.language";
        public static final String GENERATOR = "hashtools.generator.language.language";
        public static final String SHARED = "hashtools.coremodule.language.language";
    }

    public static final class PropertyKey {
        public static final String HOME_DIRECTORY = "user.home";
        public static final String JAVAFX_PRELOADER = "javafx.preloader";
    }

    public static final class PseudoClass {
        public static final javafx.css.PseudoClass ARMED = javafx.css.PseudoClass.getPseudoClass("armed");
        public static final javafx.css.PseudoClass DISABLED = javafx.css.PseudoClass.getPseudoClass("disabled");
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = ThreadPool.newCachedDaemon("GlobalThreadPool");
    }

    public static final class StaticImplementation {
        public static final Condition NO_CONDITION = new NoCondition();
        public static final Operation NO_OPERATION = new NoOperation();
        public static final String PRELOADER_CLASS = PreloaderWindow.class.getCanonicalName();
    }
}
