package hashtools.operation;

import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowNode implements Operation {

    private final Node node;

    @Override
    public void perform() {
        node.setVisible(true);
    }
}
