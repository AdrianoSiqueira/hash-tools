package hashtools.utility;

import hashtools.domain.Algorithm;

import java.util.Optional;
import java.util.stream.Stream;

public class AlgorithmFinder {

    public Optional<Algorithm> find(int length) {
        return Stream
            .of(Algorithm.values())
            .filter(lengthMatches)
            .findFirst()
            .orElse(null);
    }

    public Optional<Algorithm> find(String name) {
        return Stream
            .of(Algorithm.values())
            .filter(nameMatches)
            .findFirst()
            .orElse(null);
    }
}
