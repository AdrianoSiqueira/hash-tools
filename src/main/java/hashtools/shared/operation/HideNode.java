package hashtools.shared.operation;

import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HideNode implements Operation {

    private final Node node;

    @Override
    public void perform() {
        node.setVisible(false);
    }
}
