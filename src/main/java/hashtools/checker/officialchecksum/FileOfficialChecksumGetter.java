package hashtools.checker.officialchecksum;

import hashtools.shared.Algorithm;
import hashtools.checker.CheckerChecksum;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FileOfficialChecksumGetter implements OfficialChecksumGetter {

    private final Path file;

    @Override
    public List<CheckerChecksum> get() {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            List<CheckerChecksum> checksums = new ArrayList<>();
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

            return checksums;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
