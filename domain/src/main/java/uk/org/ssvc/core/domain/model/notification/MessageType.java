package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.SMS;

@AllArgsConstructor
@Getter
public enum MessageType {

    MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY(EMAIL),
    MEMBERSHIP_DUE_FOR_RENEWAL_NOW(SMS);

    private final NotificationChannel preferredChannel;

}
