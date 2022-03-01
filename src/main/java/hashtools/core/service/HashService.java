package hashtools.core.service;

import hashtools.core.model.HashAlgorithm;
import hashtools.core.model.Sample;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    public void generate(Sample sample) {
        if (sample == null) return;

        String hash = new FileService().stringIsFilePath(sample.getInputData())
                      ? generate(sample.getAlgorithm(), Path.of(sample.getInputData()))
                      : generate(sample.getAlgorithm(), sample.getInputData());

        sample.setCalculatedHash(hash);
    }

    public String generate(HashAlgorithm algorithm, String string) {
        byte[] stringBytes = string.getBytes();

        MessageDigest messageDigest = getMessageDigest(algorithm);
        byte[]        bytes         = messageDigest.digest(stringBytes);

        return hexBytesToString(bytes);
    }

    public String generate(HashAlgorithm algorithm, Path path) {
        MessageDigest messageDigest = getMessageDigest(algorithm);

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            byte[] buffer = new byte[1024];
            int    amountRead;

            while ((amountRead = stream.read(buffer)) > -1) {
                messageDigest.update(buffer, 0, amountRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = messageDigest.digest();
        return hexBytesToString(bytes);
    }

    private MessageDigest getMessageDigest(HashAlgorithm algorithm) {
        try {
            return MessageDigest.getInstance(algorithm.getName());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private String hexBytesToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
