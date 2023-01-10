package hashtools.core.service;

import hashtools.core.factory.DaemonFactory;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Deprecated
@Getter
public enum ParallelismService {

    CACHED_THREAD_POOL(Executors.newCachedThreadPool(
            new DaemonFactory()
    )),

    FIXED_THREAD_POOL(Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new DaemonFactory()
    ));


    private final ExecutorService executor;


    ParallelismService(ExecutorService executor) {
        this.executor = executor;
    }


    public static void shutdown() {
        FIXED_THREAD_POOL.executor.shutdown();
    }
}
