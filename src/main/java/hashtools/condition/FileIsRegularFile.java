package hashtools.condition;

import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class FileIsRegularFile implements Condition {

    private final Path file;

    @Override
    public boolean isTrue() {
        return Files.isRegularFile(file);
    }
}
