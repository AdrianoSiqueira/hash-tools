package hashtools.domain.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Algorithm {

    MD5("MD5", 32),
    SHA1("SHA-1", 40),
    SHA224("SHA-224", 56),
    SHA256("SHA-256", 64),
    SHA384("SHA-384", 96),
    SHA512("SHA-512", 128);

    private final String name;
    private final int    length;
}
