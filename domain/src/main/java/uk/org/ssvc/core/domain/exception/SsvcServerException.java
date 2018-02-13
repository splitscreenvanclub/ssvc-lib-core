package uk.org.ssvc.core.domain.exception;

public class SsvcServerException extends RuntimeException {

    public SsvcServerException(String message) {
        super(message);
    }

    public SsvcServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SsvcServerException(Throwable cause) {
        super(cause);
    }

}
