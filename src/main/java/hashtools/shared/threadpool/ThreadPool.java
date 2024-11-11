package hashtools.shared.threadpool;

import hashtools.shared.Resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static ExecutorService newCachedDaemon(String name) {
        return Executors.newCachedThreadPool(
            new DaemonThreadFactory(name)
        );
    }

    public static ExecutorService newFixedDaemon(String name) {
        return Executors.newFixedThreadPool(
            Resource.Hardware.CPU_CORES,
            new DaemonThreadFactory(name)
        );
    }
}
