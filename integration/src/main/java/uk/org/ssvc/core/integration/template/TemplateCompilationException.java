package uk.org.ssvc.core.integration.template;

public class TemplateCompilationException extends RuntimeException {

    public TemplateCompilationException(String message) {
        super(message);
    }

    public TemplateCompilationException(String message, Throwable cause) {
        super(message, cause);
    }

}
