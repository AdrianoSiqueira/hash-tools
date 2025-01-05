package hashtools.checker.officialchecksum;

import hashtools.checker.CheckerChecksum;

import java.nio.file.Path;
import java.util.List;

public interface OfficialChecksumExtractor {

    static OfficialChecksumExtractor of(String string) {
        return new StringOfficialChecksumExtractor(string);
    }

    static OfficialChecksumExtractor of(Path file) {
        return new FileOfficialChecksumExtractor(file);
    }

    List<CheckerChecksum> extract();
}
