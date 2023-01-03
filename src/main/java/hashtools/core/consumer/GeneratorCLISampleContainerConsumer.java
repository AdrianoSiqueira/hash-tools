package hashtools.core.consumer;

import hashtools.core.model.Sample;
import hashtools.core.model.SampleContainer;

public class GeneratorCLISampleContainerConsumer implements SampleContainerConsumer {

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

    private String getFileName(Sample sample) {
        return sample.isUsingInputText()
               ? sample.getInputText()
               : sample.getInputFile().getFileName().toString();
    }
}
