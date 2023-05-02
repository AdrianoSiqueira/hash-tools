package hashtools.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * Performs checksum generation operations.
 * </p>
 */
public class HashService {

    /**
     * <p>
     * Converts the hexadecimal bytes array to string.
     * </p>
     *
     * @param bytes Byte to be converted.
     *
     * @return String converted from the bytes array.
     */
    private String convertHexadecimalToString(byte[] bytes) {
        return Stream.iterate(0, i -> i < bytes.length, i -> ++i)
                     .map(i -> bytes[i])
                     .map(i -> String.format("%02x", i))
                     .map(String::toLowerCase)
                     .collect(Collectors.joining());
    }

    /**
     * <p>
     * Generates the hash checksum from the given file.
     * </p>
     *
     * @param algorithm Which checksum to generate.
     * @param file      Where the checksum will be generated from.
     *
     * @return The hash checksum converted to string.
     */
    public String generate(String algorithm, Path file) {
        return Optional.ofNullable(algorithm)
                       .map(this::getMessageDigest)
                       .map(md -> updateDigest(md, file))
                       .map(MessageDigest::digest)
                       .map(this::convertHexadecimalToString)
                       .orElse(null);
    }

    /**
     * <p>
     * Generates the hash checksum from the given text.
     * </p>
     *
     * @param algorithm Which checksum to generate.
     * @param text      Where the checksum will be generated from.
     *
     * @return The hash checksum converted to string.
     */
    public String generate(String algorithm, String text) {
        return Optional.ofNullable(algorithm)
                       .map(this::getMessageDigest)
                       .map(md -> updateDigest(md, text))
                       .map(MessageDigest::digest)
                       .map(this::convertHexadecimalToString)
                       .orElse(null);
    }

    /**
     * <p>
     * Creates the {@link MessageDigest} ready to generated checksums
     * using the given algorithm.
     * </p>
     *
     * @param algorithm Determines the checksum that will be generated.
     *
     * @return A {@link MessageDigest} instance.
     */
    private MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * <p>
     * Updates the {@link MessageDigest}'s content using the bytes from
     * the given text.
     * </p>
     *
     * @param md   MessageDigest that will be updated.
     * @param text Where to get the bytes.
     *
     * @return The updated MessageDigest.
     */
    private MessageDigest updateDigest(MessageDigest md, String text) {
        if (text == null) return null;

        md.update(text.getBytes());
        return md;
    }

    /**
     * <p>
     * Updates the {@link MessageDigest}'s content using the bytes from
     * the given file.
     * </p>
     *
     * @param md   MessageDigest that will be updated.
     * @param file Where to get the bytes.
     *
     * @return The updated MessageDigest.
     */
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
