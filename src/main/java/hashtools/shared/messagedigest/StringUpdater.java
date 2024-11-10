package hashtools.shared.messagedigest;

import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;

@RequiredArgsConstructor
public class StringUpdater implements Updater {

    private final String string;

    @Override
    public void update(MessageDigest messageDigest) {
        messageDigest.update(string.getBytes());
    }
}
