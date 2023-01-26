package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputControl;

public class GUIDataConsumer implements DataConsumer {

    private final Labeled          labeled;
    private final TextInputControl textInputControl;
    private final ProgressBar      progressBar;

    public GUIDataConsumer(Labeled labeled) {
        this.labeled          = labeled;
        this.textInputControl = null;
        this.progressBar      = null;
    }

    public GUIDataConsumer(TextInputControl textInputControl) {
        this.labeled          = null;
        this.textInputControl = textInputControl;
        this.progressBar      = null;
    }

    public GUIDataConsumer(ProgressBar progressBar) {
        this.labeled          = null;
        this.textInputControl = null;
        this.progressBar      = progressBar;
    }

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        if (labeled != null)
            labeled.setText(result);

        if (textInputControl != null)
            textInputControl.setText(result);

        if (progressBar != null)
            progressBar.setProgress(data.getSafetyPercentage() / 100.0);
    }
}
