package hashtools.checker.condition;

import hashtools.shared.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Set;

@RequiredArgsConstructor
public class ChecksumFileTypeIsValidCondition extends Condition {

    private static final Set<String> CHECKSUM_EXTENSIONS = Set.of(
        ".md5",
        ".sha1",
        ".sha224",
        ".sha256",
        ".sha384",
        ".sha512",
        ".txt"
    );

    private final Path file;

    @Override
    public boolean isTrue() {
        String fileName = file
            .getFileName()
            .toString();


        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex < 0) {
            // File has no extension
            return false;
        } else if (lastDotIndex == 0) {
            // File is hidden (unix)
            return false;
        } else if (lastDotIndex == fileName.length() - 1) {
            // File name ends with dot
            return false;
        }


        String extension = fileName.substring(lastDotIndex);
        return CHECKSUM_EXTENSIONS.contains(extension);
    }
}
