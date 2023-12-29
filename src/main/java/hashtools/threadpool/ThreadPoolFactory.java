package hashtools.threadpool;

import java.util.concurrent.ThreadFactory;

public class ThreadPoolFactory implements ThreadFactory {

    private ThreadGroup threadGroup;
    private boolean     isDaemon;

    public ThreadPoolFactory(String name, boolean isDaemon) {
        this.threadGroup = new ThreadGroup(name);
        this.isDaemon    = isDaemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = String.format(
            "%s_%d",
            threadGroup.getName(),
            threadGroup.activeCount()
        );

        Thread thread = new Thread(threadGroup, runnable, name);
        thread.setDaemon(isDaemon);
        return thread;
    }
}
