package hashtools.core.factory.hash;

import hashtools.core.factory.algorithm.NameAlgorithmFactory;
import hashtools.core.model.Hash;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeneratorHashFactory implements HashFactory {

    private final List<String> algorithmNames;

    public GeneratorHashFactory(List<String> algorithmNames) {
        this.algorithmNames = algorithmNames;
    }

    @Override
    public List<Hash> create() {
        if (algorithmNames == null)
            return null;

        return algorithmNames.stream()
                             .map(this::getHash)
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList());
    }

    private Hash getHash(String name) {
        String algorithm = new NameAlgorithmFactory(name).create();

        if (algorithm == null)
            return null;

        Hash hash = new Hash();
        hash.setAlgorithm(algorithm);
        return hash;
    }
}
