package hashtools.core.consumer;

import hashtools.core.factory.RuntimeDataFormatterFactory;
import hashtools.core.model.RuntimeData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileConsumer implements RuntimeDataConsumer {

    @Override
    public void consume(RuntimeData runtimeData) {
        String content = new RuntimeDataFormatterFactory()
                .getFormatter(runtimeData.isChecking())
                .format(runtimeData);

        writeFile(content, runtimeData.getOutputFile());
    }

    private void writeFile(String content, Path outputFile) {
        try {
            Files.writeString(
                    outputFile,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
