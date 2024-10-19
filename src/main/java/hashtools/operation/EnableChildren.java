package hashtools.operation;

import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnableChildren implements Operation {

    private final Pane pane;

    @Override
    public void perform() {
        pane
            .getChildren()
            .forEach(node -> node.setDisable(false));
    }
}
