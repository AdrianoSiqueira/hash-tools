package hashtools.core.service;

import hashtools.core.factory.AlgorithmFactory;

public class AlgorithmService {

    public boolean algorithmIsValid(String algorithm) {
        return new AlgorithmFactory().getAlgorithm(algorithm) != null;
    }
}
