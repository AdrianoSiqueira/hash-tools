package hashtools.shared.condition;

import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class FileIsMissingCondition extends Condition {

    private final Path file;

    @Override
    public boolean isTrue() {
        return !Files.isRegularFile(file);
    }
}
