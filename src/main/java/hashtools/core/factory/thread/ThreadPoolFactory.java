package hashtools.core.factory.thread;

import java.util.concurrent.ThreadFactory;

/**
 * <p>
 * Creates a thread pool with the provided name. The constructor
 * receives a boolean to indicate whether threads will be daemon.
 * </p>
 */
public abstract class ThreadPoolFactory implements ThreadFactory {

    private ThreadGroup group;
    private boolean     isDaemon;

    public ThreadPoolFactory(String name, boolean isDaemon) {
        this.group    = new ThreadGroup(name);
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = String.format(
                "%s_Thread%d",
                group.getName(),
                group.activeCount()
        );

        Thread thread = new Thread(group, runnable, name);
        thread.setDaemon(isDaemon);
        return thread;
    }
}
