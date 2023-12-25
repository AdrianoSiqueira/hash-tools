package hashtools.domain.messagedigest;

import java.security.MessageDigest;

public interface Updater {

    void update(MessageDigest messageDigest);
}
