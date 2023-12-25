package hashtools.domain.messagedigest;

import lombok.AllArgsConstructor;

import java.security.MessageDigest;

@AllArgsConstructor
public class StringUpdater implements Updater {

    private String string;

    @Override
    public void update(MessageDigest messageDigest) {
        messageDigest.update(string.getBytes());
    }
}
