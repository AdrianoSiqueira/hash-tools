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

        samples.forEach(sample -> sample.setReliabilityLevel(
                calculateReliabilityLevel(
                        sample.getOfficialHash(),
                        sample.getCalculatedHash()
                )
        ));
    }

    private static ReliabilityLevel calculateReliabilityLevel(String officialHash,
                                                              String calculatedHash) {
        if (officialHash.equals(calculatedHash)) {
            return ReliabilityLevel.SAFE;
        } else if (officialHash.equalsIgnoreCase(calculatedHash)) {
            return ReliabilityLevel.UNSURE;
        } else {
            return ReliabilityLevel.DANGEROUS;
        }
    }
}
