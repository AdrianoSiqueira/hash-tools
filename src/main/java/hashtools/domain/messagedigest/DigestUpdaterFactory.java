package hashtools.domain.messagedigest;

import java.nio.file.Path;

public class DigestUpdaterFactory {

    public static DigestUpdater create(Path file) {
        return new FileDigestUpdater(file);
    }

    public static DigestUpdater create(String string) {
        return new StringDigestUpdater(string);
    }
}
