package uk.org.ssvc.core.integration.template;

import com.github.jknack.handlebars.Handlebars;
import org.apache.commons.io.IOUtils;

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
            String templateContent = IOUtils.toString(getClass().getResource(templateName), "UTF8");

            return handlebars.compileInline(templateContent).apply(context);
        }
        catch (Exception e) {
            throw new TemplateCompilationException("Failed to render template=" + templateName, e);
        }
    }

}
