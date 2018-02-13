package uk.org.ssvc.core.integration.template;

public interface TemplateRenderer {

    String render(String templateName, Object context);

}
