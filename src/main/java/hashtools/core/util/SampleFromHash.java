package hashtools.core.util;

import hashtools.core.model.Sample;
import hashtools.core.service.HashAlgorithmService;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p>Class designed to create an object of type Sample from a hash checksum.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class SampleFromHash implements Function<String, Sample> {

    /**
     * <p>Creates an object of type Sample from a hash checksum.</p>
     *
     * <p>The object will already have the algorithm and official hash attributes
     * initialized. The algorithm is detected by analyzing the provided hash.</p>
     *
     * <p>If the hash is null or invalid, a null object is returned.</p>
     *
     * @param hash Hash used to create the object.
     *
     * @return An object of type Sample.
     *
     * @since 1.0.0
     */
    @Override
    public Sample apply(String hash) {
        return Optional.ofNullable(hash)
                       .flatMap(s -> HashAlgorithmService.searchByLength(s.length())
                                                         .map(algorithm -> new Sample()
                                                                 .setAlgorithm(algorithm)
                                                                 .setOfficialHash(s)))
                       .orElse(null);
    }
}
