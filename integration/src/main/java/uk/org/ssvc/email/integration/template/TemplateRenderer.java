package uk.org.ssvc.email.integration.template;

public interface TemplateRenderer {

    String render(String templateName, Object context);

}
