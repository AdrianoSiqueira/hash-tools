package hashtools.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum Algorithm {
    MD5("MD5", 32),
    SHA1("SHA1", 40),
    SHA224("SHA224", 56),
    SHA256("SHA256", 64),
    SHA384("SHA384", 96),
    SHA512("SHA512", 128);

    private final String name;
    private final int length;

    public static Optional<Algorithm> from(int length) {
        return Stream
            .of(Algorithm.values())
            .filter(algorithm -> algorithm.getLength() == length)
            .findFirst();
    }

    public static Optional<Algorithm> from(String name) {
        String searchName = name
            .toUpperCase()
            .replaceAll("[^A-Z0-9]", "");

        return Stream
            .of(Algorithm.values())
            .filter(algorithm -> algorithm.getName().equals(searchName))
            .findFirst();
    }
}
