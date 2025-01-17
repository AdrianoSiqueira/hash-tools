package hashtools.coremodule.operation;


import hashtools.coremodule.Resource;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArmNodeOperation extends Operation {

    private final Node node;

    @Override
    protected void perform() {
        node.pseudoClassStateChanged(
            Resource.PseudoClass.ARMED,
            true
        );
    }
}
