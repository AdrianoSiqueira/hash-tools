package hashtools.core.module.generator;

import hashtools.core.model.Sample;
import hashtools.core.service.HashService;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * <p>
 * Dedicated class for generating hashes. It is designed to be used within a
 * stream.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class HashGenerator implements Consumer<Sample> {

    /**
     * <p>
     * Calculates the hash of the object using the algorithm contained in the sample.
     * </p>
     *
     * <p>
     * The generated hash is stored within the sample itself.
     * </p>
     *
     * @param sample Sample containing the data for processing.
     *
     * @since 1.0.0
     */
    @Override
    public void accept(Sample sample) {
        String hash = sample.isUsingFileAsObject()
                      ? HashService.generate(sample.getAlgorithm(), (Path) sample.getObject())
                      : HashService.generate(sample.getAlgorithm(), (String) sample.getObject());

        sample.setCalculatedHash(hash);
    }
}
