package hashtools.coremodule.operation;

import hashtools.coremodule.Resource;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StopSplashOperation extends Operation {

    private final Pane pnlRoot;

    @Override
    protected void perform() {
        pnlRoot.setCursor(Cursor.DEFAULT);
        pnlRoot.pseudoClassStateChanged(
            Resource.PseudoClass.DISABLED,
            false
        );

        pnlRoot
            .getChildren()
            .forEach(node -> node.setDisable(false));
    }
}