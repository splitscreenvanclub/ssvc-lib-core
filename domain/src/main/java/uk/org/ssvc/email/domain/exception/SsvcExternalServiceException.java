package uk.org.ssvc.email.domain.exception;

public class SsvcExternalServiceException extends RuntimeException {

    public SsvcExternalServiceException(String message) {
        super(message);
    }

    public SsvcExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
