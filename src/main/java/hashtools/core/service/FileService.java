package hashtools.core.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    public List<String> readLines(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<String> readOfficialFile(Path file) {
        return readLines(file)
                .stream()
                .map(line -> line.split(" "))
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    public void replaceContent(String content, Path file) {
        try {
            Files.writeString(
                    file,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
