package hashtools.condition;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;

// TODO Remove this class when all the three run screens are done.

@RequiredArgsConstructor
public class KeyboardKeyIsHomeKey implements Condition {

    private final KeyEvent event;

    @Override
    public boolean isTrue() {
        return event.getCode() == KeyCode.ESCAPE;
    }
}
