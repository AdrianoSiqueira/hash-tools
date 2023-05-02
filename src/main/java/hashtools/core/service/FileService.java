package hashtools.core.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * Performs operations involving files.
 * </p>
 */
public class FileService {

    /**
     * <p>
     * Reads all lines of the file.
     * </p>
     *
     * @param file File to be read.
     *
     * @return A list with the lines.
     */
    public List<String> readLines(Path file) {
        if (file == null)
            return List.of();

        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * <p>
     * Reads the official file separating the data from its lines.
     * </p>
     *
     * @param file File to be read.
     *
     * @return A list with the official hashes.
     */
    public List<String> readOfficialFile(Path file) {
        return readLines(file)
                .stream()
                .map(line -> line.split(" "))
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    /**
     * <p>
     * Replaces the content of the file.
     * </p>
     *
     * @param content New content to be saved.
     * @param file    Where to save the content.
     */
    public void replaceContent(String content, Path file) {
        if (content == null) return;
        if (file == null) return;

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
