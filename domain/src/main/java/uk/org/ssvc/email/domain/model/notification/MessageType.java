package uk.org.ssvc.email.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static uk.org.ssvc.email.domain.model.notification.NotificationChannel.EMAIL;

@AllArgsConstructor
@Getter
public enum MessageType {

    MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY(EMAIL);
//    MEMBERSHIP_RECENTLY_LAPSED(EMAIL),
//    MEMBERSHIP_DUE_FOR_RENEWAL_NOW(EMAIL);

    private final NotificationChannel preferredChannel;

}
