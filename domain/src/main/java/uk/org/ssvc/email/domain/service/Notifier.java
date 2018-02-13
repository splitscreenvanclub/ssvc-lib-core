package uk.org.ssvc.email.domain.service;

import uk.org.ssvc.email.domain.exception.SsvcExternalServiceException;
import uk.org.ssvc.email.domain.model.notification.Message;
import uk.org.ssvc.email.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.email.domain.model.notification.Recipient;

public interface Notifier {

    NotificationSendResult sendMessage(Recipient recipient, Message message)
            throws SsvcExternalServiceException;

}
