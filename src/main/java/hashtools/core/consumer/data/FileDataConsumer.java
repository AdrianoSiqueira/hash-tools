package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import hashtools.core.service.FileService;

import java.nio.file.Path;

public class FileDataConsumer implements DataConsumer {

    private final Path outputFile;

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
