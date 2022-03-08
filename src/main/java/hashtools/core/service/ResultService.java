package hashtools.core.service;

import hashtools.core.model.Result;
import hashtools.core.model.Sample;

import java.util.List;

public class ResultService {

    public double calculateReliabilityPercentage(List<Sample> samples) {
        if (samples.isEmpty()) return 0.0;

        return samples.stream()
                      .map(Sample::getResult)
                      .filter(result -> result == Result.SAFE)
                      .count()
               * 100.0 / samples.size();
    }

    public Result calculateResult(Sample sample) {
        return sample.getCalculatedHash().equalsIgnoreCase(sample.getOfficialHash())
               ? Result.SAFE
               : Result.UNSAFE;
    }
}