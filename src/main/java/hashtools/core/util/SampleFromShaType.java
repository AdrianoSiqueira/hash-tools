package hashtools.core.util;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Class designed to create an object of type Sample from a {@link HashAlgorithm}.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class SampleFromShaType implements Function<HashAlgorithm, Sample> {

    /**
     * <p>Creates an object of type Sample from a {@link HashAlgorithm} object.</p>
     *
     * <p>The object will already have the algorithm attribute initialized.</p>
     *
     * <p>If the {@link HashAlgorithm} is null, a null object is returned.</p>
     *
     * @param shaType Used to create the object.
     *
     * @return An object of type Sample.
     *
     * @since 1.0.0
     */
    @Override
    public Sample apply(HashAlgorithm shaType) {
        return Optional.ofNullable(shaType)
                       .map(algorithm -> new Sample().setAlgorithm(algorithm))
                       .orElse(null);
    }
}
