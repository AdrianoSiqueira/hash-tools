package hashtools.core.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NewParallelismService {

    private ExecutorService executor;

    public NewParallelismService() {
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public NewParallelismService(ExecutorService executor) {
        this.executor = executor;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public final void shutdown() {
        executor.shutdown();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }
}
