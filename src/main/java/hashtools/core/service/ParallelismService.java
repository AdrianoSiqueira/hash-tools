package hashtools.core.service;

import hashtools.core.factory.DaemonFactory;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public enum ParallelismService {

    INSTANCE(Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new DaemonFactory()
    )),

    CACHED_THREAD_POOL(Executors.newCachedThreadPool(
            new DaemonFactory()
    ));


    private final ExecutorService executor;


    ParallelismService(ExecutorService executor) {
        this.executor = executor;
    }


    public static void shutdown() {
        INSTANCE.executor.shutdown();
    }
}
