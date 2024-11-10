package hashtools.shared.condition;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MouseButtonIsPrimary implements Condition {

    private final MouseEvent event;

    @Override
    public boolean isTrue() {
        return event.getButton() == MouseButton.PRIMARY;
    }
}
