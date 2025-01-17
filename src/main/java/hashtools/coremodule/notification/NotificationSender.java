package hashtools.coremodule.notification;

public interface NotificationSender {

    Notification getCallerNotification();

    void registerNotificationReceiver(NotificationReceiver receiver);

    void sendNotification(Notification notification);
}
