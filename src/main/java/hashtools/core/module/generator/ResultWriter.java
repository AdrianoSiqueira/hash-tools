package hashtools.core.module.generator;

import aslib.filemanager.FileWriter;
import hashtools.core.model.Sample;

import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * <p>
 * Dedicated class for writing calculated hashes to a file. It is designed to be used within a stream.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class ResultWriter implements Consumer<Sample> {

    private final Path destination;


    /**
     * <p>
     * Creates an instance of the {@link ResultWriter} class.
     * </p>
     *
     * @param destination File where data will be saved.
     *
     * @since 1.0.0
     */
    public ResultWriter(Path destination) {
        this.destination = destination;
    }


    /**
     * <p>
     * Appends the calculated hash of the sample to the end of the file.
     * </p>
     *
     * <p>
     * The file will be created if necessary.
     * </p>
     *
     * @param sample Sample containing the data. Not null.
     *
     * @since 1.0.0
     */
    @Override
    public void accept(Sample sample) {
        StringBuilder content = new StringBuilder()
                .append(sample.getCalculatedHash())
                .append("  ");

        if (sample.isUsingFileAsObject()) {
            Path path = (Path) sample.getObject();
            content.append(path.getFileName().toString());
        } else {
            content.append(sample.getObject());
        }

        content.append(System.lineSeparator());

        new FileWriter()
                .append(content.toString(), destination);
    }
}
