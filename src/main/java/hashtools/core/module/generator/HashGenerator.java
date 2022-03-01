package hashtools.core.module.generator;

import hashtools.core.model.HashAlgorithm;
import lombok.SneakyThrows;

import java.security.MessageDigest;

public interface HashGenerator<T> {

    String generate(HashAlgorithm algorithm, T t);


    @SneakyThrows
    default MessageDigest getMessageDigest(HashAlgorithm algorithm) {
        return MessageDigest.getInstance(algorithm.getName());
    }

    default String hexBytesToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
