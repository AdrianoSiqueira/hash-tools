package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;

public class GUIDataConsumer implements DataConsumer {

    private final Labeled          labeled;
    private final TextInputControl textInputControl;

    public GUIDataConsumer(Labeled labeled) {
        this.labeled          = labeled;
        this.textInputControl = null;
    }

    public GUIDataConsumer(TextInputControl textInputControl) {
        this.labeled          = null;
        this.textInputControl = textInputControl;
    }

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        if (labeled != null)
            labeled.setText(result);

        if (textInputControl != null)
            textInputControl.setText(result);
    }
}
