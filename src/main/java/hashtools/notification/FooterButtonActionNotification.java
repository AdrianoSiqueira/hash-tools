package hashtools.notification;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class FooterButtonActionNotification implements Notification {
    private final EventHandler<ActionEvent> btnFooterBackAction;
    private final EventHandler<ActionEvent> btnFooterNextAction;
}
