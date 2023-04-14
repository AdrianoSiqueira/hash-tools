package hashtools.core.factory.hash;

import hashtools.core.model.Hash;
import hashtools.core.service.AlgorithmService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeneratorHashFactory implements HashFactory {

    private List<String>     algorithmNames;
    private AlgorithmService service;

    public GeneratorHashFactory(List<String> algorithmNames) {
        this.algorithmNames = algorithmNames;
        this.service        = new AlgorithmService();
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
        String algorithm = service.getAlgorithm(name);

        if (algorithm == null)
            return null;

        Hash hash = new Hash();
        hash.setAlgorithm(algorithm);
        return hash;
    }
}
