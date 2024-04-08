package hashtools.domain;

import hashtools.domain.algorithm.Algorithm;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChecksumPair {

    private Algorithm algorithm;
    private String    checksum1;
    private String    checksum2;

    public final boolean matches() {
        return checksum1 != null &&
               checksum1.equalsIgnoreCase(checksum2);
    }
}
