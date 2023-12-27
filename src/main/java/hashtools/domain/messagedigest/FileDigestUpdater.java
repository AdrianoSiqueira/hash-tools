package hashtools.domain.messagedigest;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

@AllArgsConstructor
public class FileDigestUpdater implements DigestUpdater {

    private static final int BUFFER_SIZE = 2048;

    private Path file;

    @Override
    public void update(MessageDigest messageDigest) {
        try (InputStream stream = Files.newInputStream(file)) {
            int    read;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((read = stream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
