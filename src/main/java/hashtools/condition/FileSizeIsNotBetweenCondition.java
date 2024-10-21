package hashtools.condition;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class FileSizeIsNotBetweenCondition implements Condition {

    private final Path file;
    private final long minSize;
    private final long maxSize;

    @Override
    public boolean isTrue() {
        try {
            long fileSize = Files.size(file);

            return fileSize < minSize
                || fileSize > maxSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
