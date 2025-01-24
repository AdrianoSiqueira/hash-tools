package hashtools.coremodule.checksumgenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ChecksumGenerator {

    protected final String decode(byte[] bytes) {
        return IntStream
            .range(0, bytes.length)
            .mapToObj(i -> bytes[i])
            .map("%02x"::formatted)
            .collect(Collectors.joining());
    }

    public abstract String generate()
    throws IOException, NoSuchAlgorithmException;
}
