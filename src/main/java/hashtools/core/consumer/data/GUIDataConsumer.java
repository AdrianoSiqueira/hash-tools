package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import javafx.scene.control.Labeled;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputControl;

import java.util.Optional;

/**
 * <p>
 * Consumes the {@link Data} object putting its data into one of the given
 * controls.
 * </p>
 */
public class GUIDataConsumer implements DataConsumer {

    private final Labeled          labeled;
    private final TextInputControl textInputControl;
    private final ProgressBar      progressBar;

    /**
     * <p>
     * Creates an instance of {@link GUIDataConsumer} that will write the
     * formatted content into the labeled control.
     * </p>
     *
     * @param labeled Where to write the content.
     */
    public GUIDataConsumer(Labeled labeled) {
        this.labeled          = labeled;
        this.textInputControl = null;
        this.progressBar      = null;
    }

    /**
     * <p>
     * Creates an instance of {@link GUIDataConsumer} that will write the
     * formatted content into the given text input control.
     * </p>
     *
     * @param textInputControl Where to write the content.
     */
    public GUIDataConsumer(TextInputControl textInputControl) {
        this.labeled          = null;
        this.textInputControl = textInputControl;
        this.progressBar      = null;
    }

    /**
     * <p>
     * Creates an instance of {@link GUIDataConsumer} that will set the
     * safety percentage into the given progress bar.
     * </p>
     *
     * @param progressBar Where to set the percentage.
     */
    public GUIDataConsumer(ProgressBar progressBar) {
        this.labeled          = null;
        this.textInputControl = null;
        this.progressBar      = progressBar;
    }

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        Optional.ofNullable(labeled)
                .ifPresent(l -> l.setText(result));

        Optional.ofNullable(textInputControl)
                .ifPresent(t -> t.setText(result));

        Optional.ofNullable(progressBar)
                .ifPresent(p -> p.setProgress(data.getSafetyPercentage() / 100.0));
    }
}
