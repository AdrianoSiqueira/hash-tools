package hashtools.domain.algorithm;

import java.util.Optional;
import java.util.stream.Stream;

final class AlgorithmFactory {

    private static final String NON_ALPHANUMERIC = "[^a-zA-Z0-9]";

    static Optional<Algorithm> get(int length) {
        return Stream
            .of(Algorithm.values())
            .filter(algorithm -> algorithm.getLength() == length)
            .findFirst();
    }

    static Optional<Algorithm> get(String name) {
        var searchName = Optional
            .ofNullable(name)
            .map(n -> n.replaceAll(NON_ALPHANUMERIC, ""))
            .map(String::toUpperCase)
            .orElse(null);

        return Stream
            .of(Algorithm.values())
            .filter(algorithm -> algorithm
                .getName()
                .equals(searchName))
            .findFirst();
    }
}
