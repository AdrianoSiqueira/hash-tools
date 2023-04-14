package hashtools.core.service;

import hashtools.core.factory.thread.DaemonFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Deprecated(forRemoval = true)
public enum ParallelismService {

    CACHED_THREAD_POOL(Executors.newCachedThreadPool(
            new DaemonFactory())
    ),

    WORK_STEALING_THREAD_POOL(Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new DaemonFactory()
    ));

    private final ExecutorService executor;

    ParallelismService(ExecutorService executor) {
        this.executor = executor;
    }

    public static void shutdown() {
        CACHED_THREAD_POOL.executor.shutdownNow();
        WORK_STEALING_THREAD_POOL.executor.shutdown();
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
