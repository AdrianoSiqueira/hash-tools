package hashtools.coremodule.condition;

import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyboardKeyIsActionKeyCondition extends Condition {

    private final KeyCode keyCode;

    @Override
    public boolean isTrue() {
        return keyCode == KeyCode.ENTER;
    }
}
