package hashtools.checker.condition;

import hashtools.shared.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ChecksumFileSizeIsValidCondition extends Condition {

    private static final int MIN = 1;
    private static final int MAX = 5000;

    private final Path file;

    @Override
    public boolean isTrue() {
        try {
            long size = Files.size(file);

            return size >= MIN
                && size <= MAX;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
