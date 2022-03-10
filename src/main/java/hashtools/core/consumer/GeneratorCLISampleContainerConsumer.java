package hashtools.core.consumer;

import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;
import hashtools.core.service.FileService;

import java.nio.file.Path;

public class GeneratorCLISampleContainerConsumer implements SampleContainerConsumer {

    private String getFileName(Sample sample) {
        return new FileService().stringIsFilePath(sample.getInputData())
               ? Path.of(sample.getInputData()).getFileName().toString()
               : sample.getInputData();
    }


    @Override
    public void accept(SampleContainer sampleContainer) {
        System.out.println(formatResult(sampleContainer));
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
