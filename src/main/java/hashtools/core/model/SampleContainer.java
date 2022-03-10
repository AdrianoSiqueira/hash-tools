package hashtools.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class SampleContainer {

    private List<Sample> samples;
    private double       reliabilityPercentage;


    public SampleContainer() {
        this.samples = new ArrayList<>();
        this.reliabilityPercentage = 0.0;
    }


    public void add(Sample... samples) {
        Arrays.stream(samples)
              .filter(Objects::nonNull)
              .forEach(this.samples::add);
    }
}
