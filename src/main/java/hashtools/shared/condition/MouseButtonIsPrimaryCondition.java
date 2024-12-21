package hashtools.shared.condition;

import javafx.scene.input.MouseButton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MouseButtonIsPrimaryCondition extends Condition {

    private final MouseButton mouseButton;

    @Override
    public boolean isTrue() {
        return mouseButton == MouseButton.PRIMARY;
    }
}
