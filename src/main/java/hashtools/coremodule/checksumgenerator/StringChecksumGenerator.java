package hashtools.coremodule.checksumgenerator;

import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
public class StringChecksumGenerator extends ChecksumGenerator {

    private final Algorithm algorithm;
    private final String string;

    @Override
    public String generate()
    throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm.getName());
        messageDigest.update(string.getBytes());

        byte[] bytes = messageDigest.digest();
        return decode(bytes);
    }
}
