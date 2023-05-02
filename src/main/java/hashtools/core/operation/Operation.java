package hashtools.core.operation;

/**
 * <p>
 * Simple interface to perform some operation in an object of
 * type {@link T}.
 * </p>
 *
 * @param <T> Type of object the operation will be performed in.
 */
public interface Operation<T> {

    void perform(T t);
}
