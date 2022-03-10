package hashtools.core.consumer;

import hashtools.core.model.SampleContainer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
public class GeneratorGUISampleContainerConsumer implements SampleContainerConsumer {

    private final Path outputFile;


    @SneakyThrows
    @Override
    public void accept(SampleContainer sampleContainer) {
        String content = formatResult(sampleContainer);
        Files.writeString(outputFile, content, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    @Override
    public String formatResult(SampleContainer sampleContainer) {
        return new GeneratorCLISampleContainerConsumer().formatResult(sampleContainer);
    }
}
