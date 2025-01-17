package hashtools.coremodule.operation;

import hashtools.coremodule.Resource;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartSplashOperation extends Operation {

    private final Pane pnlRoot;

    @Override
    protected void perform() {
        pnlRoot.setCursor(Cursor.WAIT);
        pnlRoot.pseudoClassStateChanged(
            Resource.PseudoClass.DISABLED,
            true
        );

        pnlRoot
            .getChildren()
            .forEach(node -> node.setDisable(true));
    }
}
