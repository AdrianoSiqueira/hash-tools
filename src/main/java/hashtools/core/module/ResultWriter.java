package hashtools.core.module;

import hashtools.core.model.Sample;
import hashtools.core.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

public class ResultWriter implements Consumer<Sample> {

    private final Path destination;


    public ResultWriter(Path destination) {
        this.destination = destination;
    }


    @Override
    public void accept(Sample sample) {
        StringBuilder content = new StringBuilder()
                .append(sample.getCalculatedHash())
                .append("  ");

        if (new FileService().stringIsFilePath(sample.getInputData())) {
            Path path = Path.of(sample.getInputData());
            content.append(path.getFileName().toString());
        } else {
            content.append(sample.getInputData());
        }

        content.append(System.lineSeparator());

        try {
            Files.writeString(destination, content.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
