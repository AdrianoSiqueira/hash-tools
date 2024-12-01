package hashtools.shared.operation;

import hashtools.shared.notification.Notification;
import hashtools.shared.notification.NotificationSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendNotification extends Operation {

    private final NotificationSender sender;
    private final Notification notification;

    @Override
    protected void perform() {
        sender.sendNotification(notification);
    }
}
