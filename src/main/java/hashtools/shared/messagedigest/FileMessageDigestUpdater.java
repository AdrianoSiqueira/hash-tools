package hashtools.shared.messagedigest;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

@RequiredArgsConstructor
public class FileMessageDigestUpdater implements MessageDigestUpdater {

    private static final int BUFFER_SIZE = 2048;
    private static final int BUFFER_OFFSET = 0;

    private final Path file;

    @Override
    public void update(MessageDigest messageDigest)
    throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = stream.read(buffer)) != -1) {
                messageDigest.update(
                    buffer,
                    BUFFER_OFFSET,
                    bytesRead
                );
            }
        }
    }
}
