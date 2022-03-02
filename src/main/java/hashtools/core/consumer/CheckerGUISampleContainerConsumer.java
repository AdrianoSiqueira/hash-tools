package hashtools.core.consumer;

import hashtools.core.model.SampleContainer;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class CheckerGUISampleContainerConsumer implements SampleContainerConsumer {

    private final ProgressBar progressBar;
    private final Label       label;


    public CheckerGUISampleContainerConsumer(ProgressBar progressBar, Label label) {
        this.progressBar = progressBar;
        this.label = label;
    }


    @Override
    public void accept(SampleContainer sampleContainer) {
        progressBar.setProgress(sampleContainer.getReliabilityPercentage());
        label.setText(formatResult(sampleContainer));
    }
}
