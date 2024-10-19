package hashtools.operation;

import hashtools.notification.Notification;
import hashtools.notification.NotificationSender;
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
