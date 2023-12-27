package hashtools.domain.messagedigest;

import lombok.AllArgsConstructor;

import java.security.MessageDigest;

@AllArgsConstructor
public class StringDigestUpdater implements DigestUpdater {

    private String string;

    @Override
    public void update(MessageDigest messageDigest) {
        messageDigest.update(string.getBytes());
    }
}
