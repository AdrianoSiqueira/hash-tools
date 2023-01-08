package hashtools.core.consumer;

import hashtools.core.formatter.Formatter;
import hashtools.core.formatter.RuntimeDataFormatter;
import hashtools.core.model.RuntimeData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileConsumer implements RuntimeDataConsumer {

    @Override
    public void consume(RuntimeData runtimeData) {
        Formatter<RuntimeData> formatter = new RuntimeDataFormatter();
        String                 content   = formatter.format(runtimeData);

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
