package hashtools.core.module.checker;

import hashtools.core.model.Sample;
import hashtools.core.util.LineSplitter;
import hashtools.core.util.SampleFromHash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * Dedicated class for creating a list of {@link Sample} from the contents of a
 * file.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class SamplesFromFile implements Function<Path, List<Sample>> {

    /**
     * <p>
     * Scan lines for valid hashes. For each one found, a sample will be created.
     * </p>
     *
     * <p>
     * This method returns a list of {@link Sample} created from valid hashes.
     * If no valid hash is found, the list will be empty.
     * </p>
     *
     * @return A list of samples.
     *
     * @since 1.0.0
     */
    private List<Sample> processLines(List<String> lines) {
        return lines.stream()
                    .flatMap(new LineSplitter())
                    .map(new SampleFromHash())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
    }


    /**
     * <p>
     * Scan the file for valid hashes. For each one found, a sample will be
     * created.
     * </p>
     *
     * <p>
     * This method returns a list of samples. If the file is null or does not
     * contain valid hashes, the list will be empty.
     * </p>
     *
     * @param hashFile File that will be scanned.
     *
     * @return A list of {@link Sample}.
     *
     * @since 1.0.0
     */
    @Override
    public List<Sample> apply(Path hashFile) {
        if (hashFile == null) return new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(hashFile);
            return processLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
