package hashtools.utility;

import hashtools.domain.Algorithm;
import hashtools.domain.messagedigest.Updater;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChecksumGenerator {

    private String decode(byte[] bytes) {
        return IntStream
            .range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .map(b -> String.format("%02x", b))
            .collect(Collectors.joining())
            .toLowerCase();
    }

    public String generate(Algorithm algorithm, Updater updater) {
        MessageDigest messageDigest = getMessageDigest(algorithm);
        updater.update(messageDigest);

        byte[] bytes = messageDigest.digest();
        return decode(bytes);
    }

    private MessageDigest getMessageDigest(Algorithm algorithm) {
        try {
            return MessageDigest.getInstance(algorithm.getName());
        } catch (NoSuchAlgorithmException e) {
            // Never reached by design
            throw new RuntimeException(e);
        }
    }
}
