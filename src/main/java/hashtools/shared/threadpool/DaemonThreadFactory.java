package hashtools.shared.threadpool;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;

@RequiredArgsConstructor
class DaemonThreadFactory implements ThreadFactory {

    private final String threadPoolName;
    private ThreadGroup group;

    @Override
    public Thread newThread(Runnable runnable) {
        if (group == null) {
            group = new ThreadGroup(threadPoolName);
        }

        String threadName = "%s_thread-%d".formatted(
            group.getName(),
            group.activeCount()
        );

        Thread thread = new Thread(group, runnable);
        thread.setName(threadName);
        thread.setDaemon(true);
        return thread;
    }
}
