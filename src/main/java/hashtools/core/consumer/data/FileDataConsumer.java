package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import hashtools.core.service.FileService;

import java.nio.file.Path;

/**
 * <p>
 * Consumes the {@link Data} object writing its formatted content to the
 * given file.
 * </p>
 */
public class FileDataConsumer implements DataConsumer {

    private final Path outputFile;

    /**
     * <p>
     * Creates an instance of {@link FileDataConsumer}.
     * </p>
     *
     * @param outputFile Where to write the content.
     */
    public FileDataConsumer(Path outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        new FileService().replaceContent(result, outputFile);
    }
}
