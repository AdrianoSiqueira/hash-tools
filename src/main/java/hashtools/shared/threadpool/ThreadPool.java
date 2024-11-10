package hashtools.shared.threadpool;

import hashtools.shared.Resource;

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
            Resource.Hardware.CPU_CORES,
            new DaemonThreadFactory()
        );
    }
}
