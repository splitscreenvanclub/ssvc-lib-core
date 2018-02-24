package uk.org.ssvc.core.integration.sms;

import uk.org.ssvc.core.domain.exception.SsvcExternalServiceException;

public class NexmoSmsException extends SsvcExternalServiceException {

    public NexmoSmsException(String message) {
        super(message);
    }

    public NexmoSmsException(String message, Throwable cause) {
        super(message, cause);
    }

}
