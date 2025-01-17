package hashtools.checker.condition;

import hashtools.coremodule.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class ChecksumFileSizeIsValidCondition extends Condition {

    private static final int ONE_BYTE = 1;
    private static final int THREE_KIBIBYTE = 3072;

    private final Path file;

    @Override
    public boolean isTrue() {
        try {
            long size = Files.size(file);

            return size >= ONE_BYTE
                && size <= THREE_KIBIBYTE;
        } catch (Exception e) {
            return false;
        }
    }
}
