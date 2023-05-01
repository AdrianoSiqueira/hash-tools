package hashtools.core.formatter;

/**
 * <p>
 * Simple interface to format objects of type {@link T} into string objects.
 * </p>
 *
 * @param <T> Type of the object to format.
 */
public interface Formatter<T> {

    /**
     * <p>
     * Formats the object.
     * </p>
     *
     * @param t Object that will be formatted.
     *
     * @return The formatted result.
     */
    String format(T t);
}
