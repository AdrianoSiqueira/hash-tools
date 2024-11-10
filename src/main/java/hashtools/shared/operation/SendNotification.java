package hashtools.shared.operation;

import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotification implements Operation {

    private final NotificationSender sender;
    private final Notification notification;

    @Override
    public void perform() {
        sender.sendNotification(notification);
    }
}
