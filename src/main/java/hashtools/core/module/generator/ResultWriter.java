package hashtools.core.module.generator;

import hashtools.core.model.Sample;

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

        if (sample.isUsingFileAsObject()) {
            Path path = (Path) sample.getObject();
            content.append(path.getFileName().toString());
        } else {
            content.append(sample.getObject());
        }

        content.append(System.lineSeparator());

        try {
            Files.writeString(destination, content.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
