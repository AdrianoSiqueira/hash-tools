package hashtools.core.factory.thread;

/**
 * <p>
 * Creates a thread pool with the provided name and
 * daemon threads.
 * </p>
 */
public class DaemonThreadPoolFactory extends ThreadPoolFactory {

    public DaemonThreadPoolFactory(String name) {
        super(name, true);
    }
}
