package uk.org.ssvc.email.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.org.ssvc.email.domain.service.Notifier;

import java.util.function.Function;

import static uk.org.ssvc.email.domain.model.notification.SendStatus.SENT;
import static uk.org.ssvc.email.domain.service.SsvcRegistry.*;

@AllArgsConstructor
@Slf4j
public enum NotificationChannel implements Notifier {

    SMS(req -> smsService().sendMessage(req.recipient, req.message)),
    EMAIL(req -> emailService().sendMessage(req.recipient, req.message));

    private final Function<SendRequest, NotificationSendResult> sendFn;

    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        if (isDryRun()) {
            log.info("Would send notification but dry run only; recipient={} message={}",
                recipient, message);
            return new NotificationSendResult(recipient, SENT, this);
        }

        return sendFn.apply(new SendRequest(recipient, message));
    }

    public NotificationChannel nextBestChannel() {
        return this == SMS ? EMAIL : SMS;
    }

    @AllArgsConstructor
    private class SendRequest {
        private final Recipient recipient;
        private final Message message;
    }

}
