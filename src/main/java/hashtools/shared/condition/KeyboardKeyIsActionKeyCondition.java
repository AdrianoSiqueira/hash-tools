package hashtools.shared.condition;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyboardKeyIsActionKeyCondition extends Condition {

    private final KeyEvent event;

    @Override
    public boolean isTrue() {
        return event.getCode() == KeyCode.ENTER;
    }
}
