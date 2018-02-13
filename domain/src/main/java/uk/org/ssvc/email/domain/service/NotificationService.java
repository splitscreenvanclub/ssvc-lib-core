package uk.org.ssvc.email.domain.service;

import uk.org.ssvc.email.domain.model.notification.Message;
import uk.org.ssvc.email.domain.model.notification.NotificationChannel;
import uk.org.ssvc.email.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.email.domain.model.notification.Recipient;

import javax.inject.Inject;
import javax.inject.Singleton;

import static uk.org.ssvc.email.domain.model.notification.SendStatus.NOT_ATTEMPTED;

@Singleton
public class NotificationService implements Notifier {

    @Inject
    public NotificationService() {

    }

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        NotificationChannel preferredChannel = message.getType().getPreferredChannel();
        NotificationChannel nextBestChannel = preferredChannel.nextBestChannel();

        if (recipient.supportsChannel(preferredChannel)) {
            return preferredChannel.sendMessage(recipient, message);
        }
        else if (recipient.supportsChannel(nextBestChannel)) {
            return nextBestChannel.sendMessage(recipient, message);
        }

        return new NotificationSendResult(recipient, NOT_ATTEMPTED, null);
    }

}
