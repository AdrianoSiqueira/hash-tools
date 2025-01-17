package hashtools.coremodule.officialchecksum;

import hashtools.applicationmodule.checker.CheckerChecksum;
import hashtools.coremodule.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileOfficialChecksumExtractor implements OfficialChecksumExtractor {

    private final Path file;

    @Override
    public List<CheckerChecksum> extract() {
        List<CheckerChecksum> checksums = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String hash = line.split(" ")[0];

                Algorithm
                    .from(hash.length())
                    .map(algorithm -> {
                        CheckerChecksum checksum = new CheckerChecksum();
                        checksum.setAlgorithm(algorithm);
                        checksum.setOfficialHash(hash);
                        return checksum;
                    })
                    .ifPresent(checksums::add);
            }
        } catch (Exception e) {
            log.error("Could not read checksum file", e);
        }

        return checksums;
    }
}
