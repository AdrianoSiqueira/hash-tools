package hashtools.core.module.generator;

import hashtools.core.model.HashAlgorithm;

public class StringHashGenerator implements HashGenerator<String> {

    @Override
    public String generate(HashAlgorithm algorithm, String string) {
        if (algorithm == null || string == null) return null;

        byte[] stringBytes = string.getBytes();
        byte[] digestBytes = getMessageDigest(algorithm).digest(stringBytes);

        return hexBytesToString(digestBytes);
    }
}
