package hashtools.core.consumer;

import hashtools.core.language.LanguageManager;
import hashtools.core.model.SampleContainer;

public class CheckerCLISampleContainerConsumer implements SampleContainerConsumer {

    @Override
    public void accept(SampleContainer sampleContainer) {
        System.out.println(formatResult(sampleContainer));
        System.out.println(">> " + LanguageManager.get("Reliability") + ": " + sampleContainer.getReliabilityPercentage() + "%");
    }
}
