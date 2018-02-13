package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {

    MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY(NotificationChannel.EMAIL);
//    MEMBERSHIP_RECENTLY_LAPSED(EMAIL),
//    MEMBERSHIP_DUE_FOR_RENEWAL_NOW(EMAIL);

    private final NotificationChannel preferredChannel;

}
