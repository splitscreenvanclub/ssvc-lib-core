package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.org.ssvc.core.domain.service.Notifier;
import uk.org.ssvc.core.domain.service.SsvcRegistry;

import java.util.function.Function;

@AllArgsConstructor
@Slf4j
public enum NotificationChannel implements Notifier {

    SMS(req -> SsvcRegistry.smsService().sendMessage(req.recipient, req.message)),
    EMAIL(req -> SsvcRegistry.emailService().sendMessage(req.recipient, req.message));

    private final Function<SendRequest, NotificationSendResult> sendFn;

    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        if (SsvcRegistry.isDryRun()) {
            log.info("Would send {} but dry run only; recipient={} message={}",
                name(), recipient, message);
            return new NotificationSendResult(recipient, SendStatus.SENT, this);
        }

        log.info("Sending {}; recipient={} message={}", name(), recipient.getId(), message.getType());
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
