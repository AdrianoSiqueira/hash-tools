package hashtools.domain;

import hashtools.threadpool.DaemonThreadFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Environment {

    public static final class Hardware {
        public static final int CPU = Runtime.getRuntime().availableProcessors();
    }

    public static final class Software {
        public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(new DaemonThreadFactory("GlobalPool"));
        public static final ResourceBundle LANGUAGE = ResourceBundle.getBundle("hashtools.language.language", Locale.getDefault());
        public static final String VERSION = "3.0.0";
    }
}
