package uk.org.ssvc.email.integration.template;

import com.github.jknack.handlebars.Handlebars;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HandlebarsTemplateRendererService implements TemplateRenderer {

    private final Handlebars handlebars;

    @Inject
    public HandlebarsTemplateRendererService() {
        handlebars = new Handlebars();
    }

    @Override
    public String render(String templateName, Object context) {
        try {
            return handlebars.compileInline(templateName).apply(context);
        }
        catch (Exception e) {
            throw new TemplateCompilationException("Failed to render template=" + templateName, e);
        }
    }

}
