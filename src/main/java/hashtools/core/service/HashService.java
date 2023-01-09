package hashtools.core.service;

import hashtools.core.model.Sample;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HashService {

    public String generate(String algorithm, String text) {
        return Optional.ofNullable(algorithm)
                       .map(this::getMessageDigest)
                       .map(md -> updateDigest(md, text))
                       .map(MessageDigest::digest)
                       .map(this::convertHexadecimalToString)
                       .orElse(null);
    }

    public String generate(String algorithm, Path file) {
        return Optional.ofNullable(algorithm)
                       .map(this::getMessageDigest)
                       .map(md -> updateDigest(md, file))
                       .map(MessageDigest::digest)
                       .map(this::convertHexadecimalToString)
                       .orElse(null);
    }

    @Deprecated
    public void generate(Sample sample) {
        if (sample == null) return;

        String hash = sample.isUsingInputText()
                      ? generate(sample.getAlgorithm(), sample.getInputText())
                      : generate(sample.getAlgorithm(), sample.getInputFile());

        sample.setCalculatedHash(hash);
    }

    private String convertHexadecimalToString(byte[] bytes) {
        return Stream.iterate(0, i -> i < bytes.length, i -> ++i)
                     .map(i -> bytes[i])
                     .map(i -> String.format("%02x", i))
                     .map(String::toLowerCase)
                     .collect(Collectors.joining());
    }

    private MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private MessageDigest updateDigest(MessageDigest md, String text) {
        if (text == null) return null;

        md.update(text.getBytes());
        return md;
    }

    private MessageDigest updateDigest(MessageDigest md, Path file) {
        if (file == null) return null;
        if (Files.notExists(file)) return null;

        try (InputStream stream = Files.newInputStream(file)) {
            byte[] buffer = new byte[10_240];
            int    read;

            while ((read = stream.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return md;
    }
}
