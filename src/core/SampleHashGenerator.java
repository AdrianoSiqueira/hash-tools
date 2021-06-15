package core;

import aslib.security.HashCalculator;
import language.LanguageManager;
import model.Sample;

import java.nio.file.Path;
import java.util.Objects;

public class SampleHashGenerator
        implements Runnable {

    private final HashCalculator calculator;
    private final Sample         sample;

    public SampleHashGenerator(HashCalculator calculator, Sample sample) {
        this.calculator = Objects.requireNonNull(calculator,
                                                 LanguageManager.get("Object.cannot.be.null."));
        this.sample     = Objects.requireNonNull(sample,
                                                 LanguageManager.get("Object.cannot.be.null."));
    }

    @Override
    public void run() {
        String hash = sample.isUsingFile()
                      ? calculator.calculate(sample.getShaType(), (Path) sample.getObject())
                      : calculator.calculate(sample.getShaType(), sample.getObject().toString());

        sample.setCalculatedHash(hash);
    }
}
