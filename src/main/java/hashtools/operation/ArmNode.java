package hashtools.operation;


import hashtools.domain.Resource;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArmNode implements Operation {

    private final Node node;

    @Override
    public void perform() {
        node.pseudoClassStateChanged(
            Resource.PseudoClass.ARMED,
            true
        );
    }
}
