package hashtools.shared;

import hashtools.shared.messagedigest.MessageDigestUpdater;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChecksumGenerator {

    private String decode(byte[] bytes) {
        return IntStream
            .range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .map("%02x"::formatted)
            .collect(Collectors.joining());
    }

    public String generate(Algorithm algorithm, MessageDigestUpdater updater) {
        MessageDigest messageDigest = getMessageDigest(algorithm);
        updater.update(messageDigest);

        byte[] bytes = messageDigest.digest();
        return decode(bytes);
    }

    private MessageDigest getMessageDigest(Algorithm algorithm) {
        try {
            return MessageDigest.getInstance(algorithm.getName());
        } catch (NoSuchAlgorithmException e) {
            // Never reach by design
            throw new RuntimeException(e);
        }
    }
}
