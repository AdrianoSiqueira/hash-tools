package hashtools.core.service;

import hashtools.core.factory.thread.DaemonFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ParallelismService {

    INSTANCE;

    private final ExecutorService executor;

    ParallelismService() {
        this.executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                new DaemonFactory()
        );
    }

    public static void shutdown() {
        INSTANCE.getExecutor().shutdown();
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
