package hashtools.core.consumer;

/**
 * <p>
 * Simple interface to consume objects of type {@link T}.
 * </p>
 *
 * @param <T> Type of the object to consume.
 */
public interface Consumer<T> {

    /**
     * <p>
     * Consumes the object.
     * </p>
     *
     * @param t Object to consume.
     */
    void consume(T t);
}
