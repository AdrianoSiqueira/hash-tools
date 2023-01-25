package hashtools.core.factory.hash;

import hashtools.core.factory.algorithm.LengthAlgorithmFactory;
import hashtools.core.model.Hash;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CheckerHashFactory implements HashFactory {

    private final List<String> officialHashes;

    public CheckerHashFactory(List<String> officialHashes) {
        this.officialHashes = officialHashes;
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

    private Hash getHash(String officialHash) {
        String algorithm = new LengthAlgorithmFactory(officialHash.length()).create();

        if (algorithm == null)
            return null;

        Hash hash = new Hash();
        hash.setAlgorithm(algorithm);
        hash.setOfficial(officialHash);
        return hash;
    }
}
