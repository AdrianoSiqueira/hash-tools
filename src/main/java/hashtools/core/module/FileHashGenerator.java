package hashtools.core.module;

import hashtools.core.model.HashAlgorithm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

public class FileHashGenerator implements HashGenerator<Path> {

    @Override
    public String generate(HashAlgorithm algorithm, Path path) {
        if (algorithm == null || path == null) return null;

        MessageDigest messageDigest = getMessageDigest(algorithm);

        try (BufferedInputStream stream = new BufferedInputStream(Files.newInputStream(path))) {
            byte[] buffer = new byte[1024];
            int    read;

            while ((read = stream.read(buffer)) > -1) {
                messageDigest.update(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] digestBytes = messageDigest.digest();
        return hexBytesToString(digestBytes);
    }
}
