package hashtools.shared.operation;

import hashtools.shared.Resource;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StopSplashScreen implements Operation {

    private final Pane pnlRoot;

    @Override
    public void perform() {
        pnlRoot.setCursor(Cursor.DEFAULT);
        pnlRoot.pseudoClassStateChanged(Resource.PseudoClass.DISABLED, false);

        pnlRoot
            .getChildren()
            .forEach(node -> node.setDisable(false));
    }
}
