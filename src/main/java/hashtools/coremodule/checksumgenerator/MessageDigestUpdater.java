package hashtools.coremodule.checksumgenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;

public interface MessageDigestUpdater {

    static MessageDigestUpdater of(String string) {
        return new StringMessageDigestUpdater(string);
    }

    static MessageDigestUpdater of(Path file) {
        return new FileMessageDigestUpdater(file);
    }

    void update(MessageDigest messageDigest)
    throws IOException;
}
