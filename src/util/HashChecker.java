package util;

import aslib.security.HashCalculator;
import model.Sample;
import model.SharedResources;

import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class HashChecker implements Callable<Sample> {

    private final Sample         sample;
    private final CountDownLatch latch;

    public HashChecker(Sample sample, CountDownLatch latch) {
        this.sample = Objects.requireNonNull(sample);
        this.latch  = Objects.requireNonNull(latch);
    }

    @Override
    public Sample call() {
        HashCalculator calculator = SharedResources.INSTANCE.getHashCalculator();
        String         hash;

        if (sample.isUsingFile()) {
            hash = calculator.calculate(sample.getShaType(),
                                        (Path) sample.getObject());
        } else {
            hash = calculator.calculate(sample.getShaType(),
                                        (String) sample.getObject());
        }

        sample.setCalculatedHash(hash);
        sample.calculateReliability();

        latch.countDown();

        return sample;
    }
}