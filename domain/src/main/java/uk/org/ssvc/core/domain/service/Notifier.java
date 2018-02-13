package uk.org.ssvc.core.domain.service;

import uk.org.ssvc.core.domain.exception.SsvcExternalServiceException;
import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;

public interface Notifier {

    NotificationSendResult sendMessage(Recipient recipient, Message message)
            throws SsvcExternalServiceException;

}
