package hashtools.core.consumer.data;

import hashtools.core.model.Data;
import javafx.scene.control.Labeled;

public class GUIDataConsumer implements DataConsumer {

    private final Labeled node;

    public GUIDataConsumer(Labeled node) {
        this.node = node;
    }

    @Override
    public void consume(Data data) {
        String result = data.getFormatter()
                            .format(data);

        node.setText(result);
    }
}
