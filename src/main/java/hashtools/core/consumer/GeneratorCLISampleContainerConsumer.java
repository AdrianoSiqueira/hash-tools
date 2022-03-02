package hashtools.core.consumer;

import hashtools.core.model.SampleContainer;

public class GeneratorCLISampleContainerConsumer implements SampleContainerConsumer {

    @Override
    public void accept(SampleContainer sampleContainer) {
        String content = new GeneratorGUISampleContainerConsumer(null).formatResult(sampleContainer);
        System.out.println(content);
    }
}
