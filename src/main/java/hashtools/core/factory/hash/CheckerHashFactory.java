package hashtools.core.factory.hash;

import hashtools.core.model.Hash;
import hashtools.core.service.AlgorithmService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CheckerHashFactory implements HashFactory {

    private List<String>     officialHashes;
    private AlgorithmService service;

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
