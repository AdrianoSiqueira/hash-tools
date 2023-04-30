package hashtools.core.factory;

/**
 * <p>
 * Simple interface to create objects of type {@link T}.
 * </p>
 *
 * @param <T> Type of object to create.
 */
public interface Factory<T> {

    /**
     * <p>
     * Create an object of type {@link T}.
     * </p>
     *
     * @return A instance of {@link T}.
     */
    T create();
}
