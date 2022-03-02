package hashtools.core.consumer;

import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;
import hashtools.core.service.FileService;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class GeneratorGUISampleContainerConsumer implements SampleContainerConsumer {

    private final Path outputFile;


    public GeneratorGUISampleContainerConsumer(Path outputFile) {
        this.outputFile = outputFile;
    }


    private String getFileName(Sample sample) {
        return new FileService().stringIsFilePath(sample.getInputData())
               ? Path.of(sample.getInputData()).getFileName().toString()
               : sample.getInputData();
    }


    @SneakyThrows
    @Override
    public void accept(SampleContainer sampleContainer) {
        String content = formatResult(sampleContainer);
        Files.writeString(outputFile, content, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    @Override
    public String formatResult(SampleContainer sampleContainer) {
        StringBuilder builder  = new StringBuilder();
        String        fileName = getFileName(sampleContainer.getSamples().get(0));

        sampleContainer.getSamples()
                       .forEach(sample -> builder.append(sample.getCalculatedHash())
                                                 .append("  ")
                                                 .append(fileName)
                                                 .append(System.lineSeparator()));

        return builder.toString();
    }
}
