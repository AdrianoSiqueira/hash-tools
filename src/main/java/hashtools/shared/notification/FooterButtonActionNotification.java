package hashtools.shared.notification;

import hashtools.shared.operation.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class FooterButtonActionNotification implements Notification {
    private final Operation btnFooterBackAction;
    private final Operation btnFooterNextAction;
}
