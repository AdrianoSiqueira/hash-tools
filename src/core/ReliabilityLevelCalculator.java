package core;

import language.LanguageManager;
import model.ReliabilityLevel;
import model.Sample;

import java.util.List;
import java.util.Objects;

public class ReliabilityLevelCalculator {

    public static void calculate(List<Sample> samples) {
        Objects.requireNonNull(samples,
                               LanguageManager.get("Object.cannot.be.null."));

        samples.forEach(sample -> {
            if (sample.getOfficialHash().equals(sample.getCalculatedHash())) {
                sample.setReliabilityLevel(ReliabilityLevel.SAFE);
            } else if (sample.getOfficialHash().equalsIgnoreCase(sample.getCalculatedHash())) {
                sample.setReliabilityLevel(ReliabilityLevel.UNSURE);
            } else {
                sample.setReliabilityLevel(ReliabilityLevel.DANGEROUS);
            }
        });
    }
}
