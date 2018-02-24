package uk.org.ssvc.core.integration.email;

import uk.org.ssvc.core.domain.exception.SsvcExternalServiceException;

public class SendGridEmailException extends SsvcExternalServiceException {

    public SendGridEmailException(String message) {
        super(message);
    }

    public SendGridEmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
