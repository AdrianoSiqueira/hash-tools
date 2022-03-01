package hashtools.core.service;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public enum ParallelismService {

    INSTANCE(Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
    ));


    private final ExecutorService executor;


    ParallelismService(ExecutorService executor) {
        this.executor = executor;
    }


    public static void shutdown() {
        INSTANCE.executor.shutdown();
    }
}
