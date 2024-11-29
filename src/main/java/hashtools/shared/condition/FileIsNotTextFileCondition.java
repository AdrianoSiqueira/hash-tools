package hashtools.shared.condition;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class FileIsNotTextFileCondition extends Condition {

    private static final String TEXT_FILE_EXTENSION = "text/plain";

    private final Path file;

    @Override
    public boolean isTrue() {
        try {
            return !Files
                .probeContentType(file)
                .equalsIgnoreCase(TEXT_FILE_EXTENSION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
