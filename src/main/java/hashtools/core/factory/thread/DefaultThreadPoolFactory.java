package hashtools.core.factory.thread;

/**
 * <p>
 * Creates a thread pool with the provided name and
 * non daemon threads.
 * </p>
 */
public class DefaultThreadPoolFactory extends ThreadPoolFactory {

    public DefaultThreadPoolFactory(String name) {
        super(name, false);
    }
}
