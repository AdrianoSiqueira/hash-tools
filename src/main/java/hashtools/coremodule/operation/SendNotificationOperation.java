package hashtools.coremodule.operation;

import hashtools.coremodule.notification.Notification;
import hashtools.coremodule.notification.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotificationOperation extends Operation {

    private final NotificationSender sender;
    private final Notification notification;

    @Override
    protected void perform() {
        sender.sendNotification(notification);
    }
}
