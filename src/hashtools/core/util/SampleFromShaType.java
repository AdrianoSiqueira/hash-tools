package hashtools.core.util;

import aslib.security.SHAType;
import hashtools.core.model.Sample;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Class designed to create an object of type Sample from a {@link SHAType}.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class SampleFromShaType implements Function<SHAType, Sample> {

    /**
     * <p>Creates an object of type Sample from a {@link SHAType} object.</p>
     *
     * <p>The object will already have the algorithm attribute initialized.</p>
     *
     * <p>If the {@link SHAType} is null, a null object is returned.</p>
     *
     * @param shaType Used to create the object.
     *
     * @return An object of type Sample.
     *
     * @since 1.0.0
     */
    @Override
    public Sample apply(SHAType shaType) {
        return Optional.ofNullable(shaType)
                       .map(algorithm -> new Sample().setAlgorithm(algorithm))
                       .orElse(null);
    }
}
