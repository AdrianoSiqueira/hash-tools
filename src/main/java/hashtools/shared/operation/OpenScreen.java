package hashtools.shared.operation;

import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationReceiver;
import hashtools.shared.notification.NotificationSender;
import hashtools.shared.notification.ScreenOpenNotification;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class OpenScreen extends Operation {

    private final NotificationReceiver receiver;
    private final String fxmlPath;
    private final ResourceBundle language;
    private final Pane pnlContent;

    @Override
    protected void perform() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            loader.setResources(language);

            Pane pane = loader.load();
            Platform.runLater(() -> pnlContent
                .getChildren()
                .setAll(pane)
            );

            NotificationSender sender = loader.getController();
            sender.registerNotificationReceiver(receiver);

            Notification notification = sender.getCallerNotification();
            receiver.receiveNotification(notification);

            receiver.receiveNotification(new ScreenOpenNotification());
        } catch (IOException e) {
            throw new RuntimeException("Failed to open the screen: '" + fxmlPath + "'", e);
        }
    }
}
