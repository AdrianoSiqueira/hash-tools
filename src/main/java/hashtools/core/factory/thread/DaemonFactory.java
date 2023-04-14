package hashtools.core.factory.thread;

import java.util.concurrent.ThreadFactory;

@Deprecated(forRemoval = true)
public class DaemonFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
