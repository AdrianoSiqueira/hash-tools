package hashtools.shared.operation;

import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnableChildren extends Operation {

    private final Pane pane;

    @Override
    protected void perform() {
        pane
            .getChildren()
            .forEach(node -> node.setDisable(false));
    }
}
