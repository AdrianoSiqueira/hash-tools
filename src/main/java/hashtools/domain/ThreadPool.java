package hashtools.domain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static ExecutorService newCachedDaemon() {
        return Executors.newCachedThreadPool(
            new DaemonThreadFactory()
        );
    }

    public static ExecutorService newFixedDaemon() {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new DaemonThreadFactory()
        );
    }
}
