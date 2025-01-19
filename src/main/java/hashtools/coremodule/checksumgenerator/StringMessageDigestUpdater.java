package hashtools.coremodule.checksumgenerator;

import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;

@RequiredArgsConstructor
public class StringMessageDigestUpdater implements MessageDigestUpdater {

    private final String string;

    @Override
    public void update(MessageDigest messageDigest) {
        messageDigest.update(string.getBytes());
    }
}
