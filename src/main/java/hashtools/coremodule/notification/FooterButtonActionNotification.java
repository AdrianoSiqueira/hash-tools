package hashtools.coremodule.notification;

import hashtools.coremodule.operation.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("ClassCanBeRecord")
public class FooterButtonActionNotification implements Notification {
    private final Operation btnFooterBackAction;
    private final Operation btnFooterNextAction;
}
