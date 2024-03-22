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
        return Thread
            .ofPlatform()
            .group(group)
            .name("%s_%s".formatted(
                group.getName(),
                group.activeCount()))
            .daemon(isDaemon)
            .unstarted(runnable);
    }
}
