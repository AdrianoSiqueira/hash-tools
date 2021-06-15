package core;

import language.LanguageManager;
import model.ReliabilityLevel;
import model.Sample;

import java.util.List;
import java.util.Objects;

public class ReliabilityPercentageCalculator {

    public static double calculate(List<Sample> samples) {
        Objects.requireNonNull(samples,
                               LanguageManager.get("Object.cannot.be.null."));

        Double reliability = samples.stream()
                                    .map(Sample::getReliabilityLevel)
                                    .map(ReliabilityLevel::getScore)
                                    .reduce(Double::sum)
                                    .orElse(0.0);

        return samples.size() > 0
               ? reliability * 100 / samples.size()
               : 0.0;
    }
}
