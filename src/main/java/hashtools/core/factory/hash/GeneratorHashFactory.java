package hashtools.core.factory.hash;

import hashtools.core.model.Hash;
import hashtools.core.service.AlgorithmService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * Creates a list of {@link Hash} using the algorithm names provided
 * in the constructor.
 * </p>
 */
public class GeneratorHashFactory implements HashFactory {

    private List<String>     algorithmNames;
    private AlgorithmService service;

    /**
     * <p>
     * Creates an instance of {@link GeneratorHashFactory}.
     * </p>
     *
     * @param algorithmNames Used to create the list of {@link Hash}.
     */
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

    /**
     * <p>
     * Creates a {@link Hash} object from the algorithm name. If the
     * name is not valid, this method returns null. Otherwise the
     * {@link Hash} will have the algorithm set.
     * </p>
     *
     * @param name Used to create the {@link Hash} object.
     *
     * @return A {@link Hash} object.
     */
    private Hash getHash(String name) {
        String algorithm = service.getAlgorithm(name);

        if (algorithm == null)
            return null;

        Hash hash = new Hash();
        hash.setAlgorithm(algorithm);
        return hash;
    }
}
