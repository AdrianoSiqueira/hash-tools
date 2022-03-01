package hashtools.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class SampleList {

    private List<Sample> samples;
    private double       reliabilityPercentage;


    public SampleList() {
        this.samples = new ArrayList<>();
        this.reliabilityPercentage = 0.0;
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
