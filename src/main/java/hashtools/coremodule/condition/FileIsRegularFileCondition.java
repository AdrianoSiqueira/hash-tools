package hashtools.coremodule.condition;

import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RequiredArgsConstructor
public class FileIsRegularFileCondition extends Condition {

    private final Path file;

    @Override
    public boolean isTrue() {
        return Optional
            .ofNullable(file)
            .map(Files::isRegularFile)
            .orElse(false);
    }
}
