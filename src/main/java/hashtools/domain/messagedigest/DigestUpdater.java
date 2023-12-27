package hashtools.domain.messagedigest;

import java.security.MessageDigest;

public interface DigestUpdater {

    void update(MessageDigest messageDigest);
}
