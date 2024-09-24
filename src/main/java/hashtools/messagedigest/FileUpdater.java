package hashtools.messagedigest;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

@RequiredArgsConstructor
public class FileUpdater implements Updater {

    private static final int
        BUFFER_SIZE = 2048,
        BUFFER_OFFSET = 0;

    private final Path file;

    @Override
    public void update(MessageDigest messageDigest) {
        try (InputStream stream = Files.newInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;

            while ((read = stream.read(buffer)) != -1) {
                messageDigest.update(buffer, BUFFER_OFFSET, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
