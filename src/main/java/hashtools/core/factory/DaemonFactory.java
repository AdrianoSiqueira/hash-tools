package hashtools.core.factory;

import java.util.concurrent.ThreadFactory;

public class DaemonFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
}
