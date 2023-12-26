package hashtools.domain.officialdata;

import hashtools.domain.Algorithm;
import hashtools.domain.Checksum;
import hashtools.utility.AlgorithmFinder;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Algorithm algorithm = new AlgorithmFinder()
            .find(string.length());

        return Optional
            .ofNullable(algorithm)
            .map(this::createChecksum)
            .map(List::of)
            .orElse(Collections.emptyList());
    }
}
