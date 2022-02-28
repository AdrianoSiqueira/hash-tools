package hashtools.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SampleList {

    private List<Sample> samples;
    private double       reliabilityPercentage;


    public SampleList() {
        this.samples = new ArrayList<>();
        this.reliabilityPercentage = 0.0;
    }


    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public double getReliabilityPercentage() {
        return reliabilityPercentage;
    }

    public void setReliabilityPercentage(double reliabilityPercentage) {
        this.reliabilityPercentage = reliabilityPercentage;
    }


    public void add(Sample... samples) {
        Arrays.stream(samples)
              .filter(Objects::nonNull)
              .forEach(this.samples::add);
    }

    public void clear() {
        samples.clear();
        reliabilityPercentage = 0.0;
    }
}
