package hashtools.applicationmodule.checker.officialchecksum;

import hashtools.applicationmodule.checker.CheckerChecksum;
import hashtools.coremodule.Algorithm;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StringOfficialChecksumExtractor implements OfficialChecksumExtractor {

    private final String string;

    @Override
    public List<CheckerChecksum> extract() {
        List<CheckerChecksum> checksums = new ArrayList<>();

        Optional
            .ofNullable(string)
            .map(String::length)
            .flatMap(Algorithm::from)
            .map(algorithm -> {
                CheckerChecksum checksum = new CheckerChecksum();
                checksum.setAlgorithm(algorithm);
                checksum.setOfficialHash(string);
                return checksum;
            })
            .ifPresent(checksums::add);

        return checksums;
    }
}
