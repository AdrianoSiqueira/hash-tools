package hashtools.domain.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Algorithm {

    MD5("MD5", 32),
    SHA1("SHA1", 40),
    SHA224("SHA224", 56),
    SHA256("SHA256", 64),
    SHA384("SHA384", 96),
    SHA512("SHA512", 128);

    private final String name;
    private final int    length;
}
