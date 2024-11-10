package hashtools.shared.messagedigest;

import java.security.MessageDigest;

public interface Updater {

    void update(MessageDigest messageDigest);
}
