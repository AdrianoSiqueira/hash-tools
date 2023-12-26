package hashtools.utility;

import hashtools.domain.Algorithm;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class AlgorithmFinder {

    public Algorithm find(int length) {
        Predicate<Algorithm> lengthMatches = algorithm -> algorithm
            .getLength() == length;

        return Stream
            .of(Algorithm.values())
            .filter(lengthMatches)
            .findFirst()
            .orElse(null);
    }

    public Algorithm find(String name) {
        Predicate<Algorithm> nameMatches = algorithm -> algorithm
            .getName()
            .replace("-", "")
            .equalsIgnoreCase(name);

        return Stream
            .of(Algorithm.values())
            .filter(nameMatches)
            .findFirst()
            .orElse(null);
    }
}
