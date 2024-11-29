package hashtools.shared.condition;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MouseButtonIsPrimaryCondition extends Condition {

    private final MouseEvent event;

    @Override
    public boolean isTrue() {
        return event.getButton() == MouseButton.PRIMARY;
    }
}
