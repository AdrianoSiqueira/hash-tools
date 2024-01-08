package hashtools.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private static ExecutorService create(String name, boolean isDaemon) {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new ThreadPoolFactory(name, isDaemon)
        );
    }

    public static ExecutorService newDaemon(String name) {
        return create(name, true);
    }

    public static ExecutorService newDefault(String name) {
        return create(name, false);
    }

    @Deprecated
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void terminate(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
