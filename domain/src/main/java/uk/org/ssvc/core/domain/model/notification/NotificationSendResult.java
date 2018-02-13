package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationSendResult {

    private final Recipient recipient;
    private final SendStatus status;
    private final NotificationChannel channelUsed;

}
