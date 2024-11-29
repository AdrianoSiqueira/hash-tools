package hashtools.checker.condition;

import hashtools.shared.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ChecksumFileTypeIsValidCondition extends Condition {

    private static final String TEXT_FILE_TYPE = "text/plain";

    private final Path file;

    @Override
    public boolean isTrue() {
        try {
            return Files
                .probeContentType(file)
                .equalsIgnoreCase(TEXT_FILE_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
