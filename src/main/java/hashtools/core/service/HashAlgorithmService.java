package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

public class HashAlgorithmService {

    public static HashAlgorithm getByLength(int length)
    throws NoSuchElementException {
        return searchByLength(length)
                .orElseThrow();
    }

    public static HashAlgorithm getByName(String name)
    throws NoSuchElementException {
        return searchByName(name)
                .orElseThrow();
    }

    public static Optional<HashAlgorithm> searchByLength(int length) {
        return Stream.of(HashAlgorithm.values())
                     .filter(a -> a.getLength() == length)
                     .findFirst();
    }

    public static Optional<HashAlgorithm> searchByName(String name) {
        String search = Optional.ofNullable(name)
                                .map(n -> n.replaceAll("[^a-zA-Z0-9]", ""))
                                .orElse(null);

        return Stream.of(HashAlgorithm.values())
                     .filter(a -> a.name().equalsIgnoreCase(search))
                     .findFirst();
    }

    public static boolean stringHasValidLength(String string) {
        return Optional.ofNullable(string)
                       .map(String::length)
                       .map(HashAlgorithmService::searchByLength)
                       .map(Optional::isPresent)
                       .orElse(false);
    }
}
