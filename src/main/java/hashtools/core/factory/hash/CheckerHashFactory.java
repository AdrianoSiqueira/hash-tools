package hashtools.core.factory.hash;

import hashtools.core.model.Hash;
import hashtools.core.service.AlgorithmService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * Creates a list of {@link Hash} using the official hashes provided
 * in the constructor.
 * </p>
 */
public class CheckerHashFactory implements HashFactory {

    private List<String>     officialHashes;
    private AlgorithmService service;

    /**
     * <p>
     * Creates an instance of {@link CheckerHashFactory}.
     * </p>
     *
     * @param officialHashes Used to create the list of {@link Hash}.
     */
    public CheckerHashFactory(List<String> officialHashes) {
        this.officialHashes = officialHashes;
        this.service        = new AlgorithmService();
    }

    @Override
    public List<Hash> create() {
        if (officialHashes == null)
            return null;

        return officialHashes.stream()
                             .map(this::getHash)
                             .filter(Objects::nonNull)
                             .collect(Collectors.toList());
    }

    /**
     * <p>
     * Creates a {@link Hash} object from the official hash, attempting
     * to detect its algorithm. If the official hash is not valid, this
     * method returns null. Otherwise the {@link Hash} will have the
     * algorithm and the official hash set.
     * </p>
     *
     * @param officialHash Used to create the {@link Hash} object.
     *
     * @return A {@link Hash} object.
     */
    private Hash getHash(String officialHash) {
        String algorithm = service.getAlgorithm(officialHash.length());

        if (algorithm == null)
            return null;

        Hash hash = new Hash();
        hash.setAlgorithm(algorithm);
        hash.setOfficial(officialHash);
        return hash;
    }
}
