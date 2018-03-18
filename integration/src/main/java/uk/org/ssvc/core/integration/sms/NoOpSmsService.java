package uk.org.ssvc.core.integration.sms;

import uk.org.ssvc.core.domain.exception.SsvcExternalServiceException;
import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;
import uk.org.ssvc.core.domain.service.SmsService;

import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.core.domain.model.notification.SendStatus.SENT;

public class NoOpSmsService implements SmsService {

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) throws SsvcExternalServiceException {
        return new NotificationSendResult(recipient, SENT, EMAIL);
    }

}
