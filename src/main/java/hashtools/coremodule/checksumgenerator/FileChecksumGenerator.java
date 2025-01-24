package hashtools.coremodule.checksumgenerator;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class FileChecksumGenerator extends ChecksumGenerator {

    private static final int ONE_MEBIBYTE = 1_048_576;
    private static final int BUFFER_OFFSET = 0;

    private final Algorithm algorithm;
    private final Path file;

    @Override
    public String generate()
    throws IOException, NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.getName());

        try (InputStream stream = Files.newInputStream(file)) {
            byte[] buffer = new byte[ONE_MEBIBYTE];
            int bytesRead;

            while ((bytesRead = stream.read(buffer)) != -1) {
                messageDigest.update(
                    buffer,
                    BUFFER_OFFSET,
                    bytesRead
                );
            }
        }

        byte[] bytes = messageDigest.digest();
        return decode(bytes);
    }
}
