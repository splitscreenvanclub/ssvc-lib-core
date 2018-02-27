package uk.org.ssvc.core.domain.service;

import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.NotificationChannel;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;
import uk.org.ssvc.core.domain.model.notification.SendStatus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationService implements Notifier {

    @Inject
    public NotificationService() {

    }

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        NotificationChannel preferredChannel = message.getType().getPreferredChannel();
        NotificationChannel nextBestChannel = preferredChannel.nextBestChannel();

        message = enrichMessageWithCommonRecipientVariables(message, recipient);

        if (recipient.supportsChannel(preferredChannel)) {
            return preferredChannel.sendMessage(recipient, message);
        }
        else if (recipient.supportsChannel(nextBestChannel)) {
            return nextBestChannel.sendMessage(recipient, message);
        }

        return new NotificationSendResult(recipient, SendStatus.NOT_ATTEMPTED, null);
    }

    private Message enrichMessageWithCommonRecipientVariables(Message message, Recipient recipient) {
        message = message.withVariable("FNAME", recipient.getSalutation());
        message = message.withVariable("SALUTATION", recipient.getSalutation());
        message = message.withVariable("ID", recipient.getId());

        return message;
    }

}
