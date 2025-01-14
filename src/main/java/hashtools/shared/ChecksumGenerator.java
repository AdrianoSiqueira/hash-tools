package hashtools.shared;

import hashtools.shared.messagedigest.MessageDigestUpdater;

import java.io.IOException;
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

    public String generate(Algorithm algorithm, MessageDigestUpdater updater)
    throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.getName());
        updater.update(messageDigest);

        byte[] bytes = messageDigest.digest();
        return decode(bytes);
    }
}
