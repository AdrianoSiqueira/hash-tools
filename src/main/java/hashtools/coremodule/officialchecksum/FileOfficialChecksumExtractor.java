package hashtools.coremodule.officialchecksum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileOfficialChecksumExtractor implements OfficialChecksumExtractor {

    private final Path file;

    @Override
    public List<String> extract()
    throws IOException {
        List<String> checksums = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String checksum = line.split(" ")[0];
                checksums.add(checksum);
            }
        }

        return checksums;
    }
}
