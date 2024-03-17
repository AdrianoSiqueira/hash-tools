package hashtools.threadpool;

import java.util.concurrent.ThreadFactory;

public abstract class AbstractThreadFactory implements ThreadFactory {

    private ThreadGroup group;
    private boolean isDaemon;

    protected AbstractThreadFactory(String name, boolean isDaemon) {
        this.group = new ThreadGroup(name);
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = String.format(
            "%s_%s",
            group.getName(),
            group.activeCount()
        );

        Thread thread = new Thread(group, runnable, name);
        thread.setDaemon(isDaemon);
        return thread;
    }
}
