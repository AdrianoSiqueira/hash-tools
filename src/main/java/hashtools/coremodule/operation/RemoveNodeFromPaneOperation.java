package hashtools.coremodule.operation;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveNodeFromPaneOperation extends Operation {

    private final Pane pane;
    private final Node node;

    @Override
    protected void perform() {
        Platform.runLater(() -> pane
            .getChildren()
            .remove(node)
        );
    }
}