package hashtools.core.service;

import hashtools.core.model.Sample;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashService {

    public void generate(Sample sample) {
        if (sample == null) return;

        String hash = sample.isUsingInputText()
                      ? generate(sample.getAlgorithm(), sample.getInputText())
                      : generate(sample.getAlgorithm(), sample.getInputFile());

        sample.setCalculatedHash(hash);
    }

    private String convertHexadecimalToString(byte[] bytes) {
        if (bytes == null) throw new NullPointerException("Array cannot be null.");

        return Stream.iterate(0, i -> i < bytes.length, i -> ++i)
                     .map(i -> bytes[i])
                     .map(i -> String.format("%02x", i))
                     .map(String::toLowerCase)
                     .collect(Collectors.joining());
    }

    private String generate(String algorithm, Path file) {
        MessageDigest messageDigest = getMessageDigest(algorithm);

        try (InputStream stream = Files.newInputStream(file)) {
            byte[] buffer = new byte[2048];
            int    read;

            while ((read = stream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertHexadecimalToString(messageDigest.digest());
    }

    private String generate(String algorithm, String text) {
        MessageDigest messageDigest = getMessageDigest(algorithm);
        messageDigest.update(text.getBytes());

        return convertHexadecimalToString(messageDigest.digest());
    }

    private MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
