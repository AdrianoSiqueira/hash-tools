package hashtools.shared.notification;

import hashtools.shared.operation.ConditionalOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class FooterButtonActionNotification implements Notification {
    private final ConditionalOperation btnFooterBackAction;
    private final ConditionalOperation btnFooterNextAction;
}
