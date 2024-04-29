package hashtools.domain.officialdata;

import hashtools.domain.Checksum;
import hashtools.domain.algorithm.Algorithm;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class StringOfficialDataGetter implements OfficialDataGetter {

    private String string;

    private Checksum createChecksum(Algorithm algorithm) {
        return Checksum
            .builder()
            .algorithm(algorithm)
            .checksum(string)
            .build();
    }

    @Override
    public List<Checksum> get() {
        return Algorithm
            .get(string.length())
            .map(this::createChecksum)
            .map(List::of)
            .orElse(Collections.emptyList());
    }
}
