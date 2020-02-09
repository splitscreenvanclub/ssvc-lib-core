package uk.org.ssvc.core.domain.service;

import lombok.extern.slf4j.Slf4j;
import uk.org.ssvc.core.domain.exception.SsvcExternalServiceException;
import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.NotificationChannel;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;
import uk.org.ssvc.core.domain.model.notification.SendStatus;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
@Slf4j
public class NotificationService implements Notifier {

    @Inject
    public NotificationService() {

    }

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        NotificationChannel preferredChannel = message.getType().getPreferredChannel();

        if (recipient.supportsChannel(preferredChannel)) {
            return sendMessageUsingChannel(recipient, message, preferredChannel);
        }
        else {
            return sendMessageUsingChannel(recipient, message, preferredChannel.nextBestChannel());
        }
    }

    public List<NotificationSendResult> sendMessagesToMultipleChannels(Recipient recipient, Message message, List<NotificationChannel> channels)
            throws SsvcExternalServiceException {
        return channels.stream()
            .map(c -> sendMessageUsingChannel(recipient, message, c))
            .collect(toList());
    }

    private NotificationSendResult sendMessageUsingChannel(Recipient recipient, Message message, NotificationChannel channel) {
        message = enrichMessageWithCommonRecipientVariables(message, recipient);

        if (recipient.supportsChannel(channel)) {
            return channel.sendMessage(recipient, message);
        }

        log.info("Not sending notification since there was channel not supported; recipient={} message={} channel={}",
            recipient.getId(), message.getType(), channel);

        return new NotificationSendResult(recipient, SendStatus.NOT_ATTEMPTED, channel);
    }

    private Message enrichMessageWithCommonRecipientVariables(Message message, Recipient recipient) {
        message = message.withVariable("fname", recipient.getSalutation());
        message = message.withVariable("salutation", recipient.getSalutation());
        message = message.withVariable("id", recipient.getId());

        return message;
    }

}
