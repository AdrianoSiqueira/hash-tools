package hashtools.applicationmodule.comparator;

import hashtools.coremodule.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComparatorChecksum {

    private Algorithm algorithm;
    private String hash1;
    private String hash2;

    public final boolean matches() {
        return hash1 != null
            && hash1.equalsIgnoreCase(hash2);
    }
}
