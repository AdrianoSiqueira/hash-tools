package hashtools.core.module.checker;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p>
 * Dedicated class to calculate the {@link Result} of a {@link Sample}.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class ResultCalculator implements Consumer<Sample> {

    /**
     * <p>
     * Calculates the sample result by comparing the official hash with the
     * calculated hash.
     * </p>
     *
     * <p>
     * If the sample is null, it will be ignored.
     * </p>
     *
     * @param sample Sample whose result will be calculated.
     *
     * @since 1.0.0
     */
    @Override
    public void accept(Sample sample) {
        Optional.ofNullable(sample)
                .ifPresent(s -> {
                    String official   = s.getOfficialHash();
                    String calculated = s.getCalculatedHash();

                    if (official.equals(calculated)) {
                        s.setResult(Result.SAFE);
                    } else if (official.equalsIgnoreCase(calculated)) {
                        s.setResult(Result.UNSURE);
                    } else {
                        s.setResult(Result.DANGEROUS);
                    }
                });
    }
}
