package hashtools.shared.messagedigest;

import java.security.MessageDigest;

public interface MessageDigestUpdater {

    void update(MessageDigest messageDigest);
}
