package hashtools.core.util;

import aslib.security.SHAType;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Class designed to create an object of type {@link SHAType} from an Object.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class ShaTypeFromObject implements Function<Object, SHAType> {

    /**
     * <p>Creates an object of type ShaType from an Object.</p>
     *
     * <p>If the object is an instance of the ShaType class, it will be returned
     * itself. Otherwise the method will try to get the object of type ShaType
     * using the String representation of the given object.</p>
     *
     * <p>If the given object is null, or if the object of type ShaType cannot
     * be obtained, then null is returned.</p>
     *
     * @param object Object used to create a ShaType object.
     *
     * @return An object of type ShaType.
     *
     * @since 1.0.0
     */
    @Override
    public SHAType apply(Object object) {
        return Optional.ofNullable(object)
                       .map(o -> {
                           if (o instanceof SHAType sha)
                               return sha;

                           try {
                               String name = o.toString()
                                              .replaceAll("-", "")
                                              .toUpperCase();
                               return SHAType.valueOf(name);
                           } catch (IllegalArgumentException ignored) {
                               return null;
                           }
                       })
                       .orElse(null);
    }
}
