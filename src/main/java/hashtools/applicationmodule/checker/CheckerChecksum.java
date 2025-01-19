package hashtools.applicationmodule.checker;

import hashtools.coremodule.checksumgenerator.Algorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CheckerChecksum {

    private Algorithm algorithm;
    private String generatedHash;
    private String officialHash;

    public final boolean matches() {
        return generatedHash != null
            && generatedHash.equalsIgnoreCase(officialHash);
    }
}
