package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Deprecated
public class HashAlgorithmService {

    @Deprecated
    public List<HashAlgorithm> convertToAlgorithmList(List<String> algorithms) {
        return Optional.ofNullable(algorithms)
                       .orElse(Collections.emptyList())
                       .stream()
                       .map(this::searchByName)
                       .filter(Optional::isPresent)
                       .map(Optional::get)
                       .collect(Collectors.toList());
    }

    @Deprecated
    public List<HashAlgorithm> convertToAlgorithmList(String... algorithms) {
        return convertToAlgorithmList(List.of(algorithms));
    }

    @Deprecated
    public HashAlgorithm getByLength(int length)
    throws NoSuchElementException {
        return searchByLength(length)
                .orElseThrow();
    }

    @Deprecated
    public HashAlgorithm getByName(String name)
    throws NoSuchElementException {
        return searchByName(name)
                .orElseThrow();
    }

    @Deprecated
    public Optional<HashAlgorithm> searchByLength(int length) {
        return Stream.of(HashAlgorithm.values())
                     .filter(a -> a.getLength() == length)
                     .findFirst();
    }

    @Deprecated
    public Optional<HashAlgorithm> searchByName(String name) {
        String search = Optional.ofNullable(name)
                                .map(n -> n.replaceAll("[^a-zA-Z0-9]", ""))
                                .orElse(null);

        return Stream.of(HashAlgorithm.values())
                     .filter(a -> a.name().equalsIgnoreCase(search))
                     .findFirst();
    }

    @Deprecated
    public boolean stringHasValidLength(String string) {
        return Optional.ofNullable(string)
                       .map(String::length)
                       .map(this::searchByLength)
                       .map(Optional::isPresent)
                       .orElse(false);
    }
}
