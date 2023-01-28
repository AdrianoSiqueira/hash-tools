package hashtools.core.service;

import hashtools.core.factory.thread.DaemonFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ParallelismService {

    INSTANCE;

    private final ExecutorService fixedThreadPool;
    private final ExecutorService cachedThreadPool;

    ParallelismService() {
        this.fixedThreadPool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                new DaemonFactory()
        );

        this.cachedThreadPool = Executors.newCachedThreadPool(new DaemonFactory());
    }

    public static void shutdown() {
        INSTANCE.getFixedThreadPool().shutdownNow();
    }

    public ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }

    public ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }
}
