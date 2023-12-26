package hashtools.domain.officialdata;

import hashtools.domain.Algorithm;
import hashtools.domain.Checksum;
import hashtools.utility.AlgorithmFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FileOfficialDataGetter implements OfficialDataGetter {

    private Path            file;
    private AlgorithmFinder finder;

    public FileOfficialDataGetter(Path file) {
        this.file   = file;
        this.finder = new AlgorithmFinder();
    }

    private Checksum createChecksum(Algorithm algorithm, String string) {
        return Checksum
            .builder()
            .algorithm(algorithm)
            .checksum(string)
            .build();
    }

    private Checksum createChecksum(String string) {
        Algorithm algorithm = finder.find(string.length());

        return Optional
            .ofNullable(algorithm)
            .map(a -> createChecksum(a, string))
            .orElse(null);
    }

    @Override
    public List<Checksum> get() {
        return readFile()
            .stream()
            .flatMap(line -> Stream.of(line.split(" ")))
            .map(this::createChecksum)
            .filter(Objects::nonNull)
            .toList();
    }

    private List<String> readFile() {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
