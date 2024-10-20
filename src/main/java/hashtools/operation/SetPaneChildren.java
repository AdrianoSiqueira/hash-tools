package hashtools.operation;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetPaneChildren implements Operation {

    private final Pane pane;
    private final Node node;

    @Override
    public void perform() {
        Platform.runLater(() -> pane
            .getChildren()
            .setAll(node)
        );
    }
}
