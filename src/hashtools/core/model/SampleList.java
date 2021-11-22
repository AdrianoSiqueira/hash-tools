package hashtools.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>Collects samples into a list to be processed. In addition to storing
 * general reliability metrics.</p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class SampleList {

    private List<Sample> samples;

    private double reliabilityPercentage;
    private double maxPossibleScore;


    public SampleList() {
        this.samples = new ArrayList<>();
        this.reliabilityPercentage = 0.0;
    }


    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
        calculateMaxPossibleScore();
    }

    public double getReliabilityPercentage() {
        return reliabilityPercentage;
    }

    public void setReliabilityPercentage(double reliabilityPercentage) {
        this.reliabilityPercentage = reliabilityPercentage;
    }

    public double getMaxPossibleScore() {
        return maxPossibleScore;
    }


    public void add(Sample... samples) {
        Arrays.stream(samples)
              .filter(Objects::nonNull)
              .forEach(this.samples::add);

        calculateMaxPossibleScore();
    }

    public void clear() {
        samples.clear();
        reliabilityPercentage = 0.0;
        maxPossibleScore = 0.0;
    }


    private void calculateMaxPossibleScore() {
        maxPossibleScore = samples.size() * Result.SAFE.getScore();
    }
}
