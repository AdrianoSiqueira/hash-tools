package hashtools.checker.officialchecksum;

import hashtools.shared.Algorithm;
import hashtools.checker.CheckerChecksum;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StringOfficialChecksumExtractor implements OfficialChecksumExtractor {

    private final String string;

    @Override
    public List<CheckerChecksum> extract() {
        return Algorithm
            .from(string.length())
            .map(algorithm -> {
                CheckerChecksum checksum = new CheckerChecksum();
                checksum.setAlgorithm(algorithm);
                checksum.setOfficialHash(string);
                return checksum;
            })
            .map(List::of)
            .orElse(List.of());
    }
}
