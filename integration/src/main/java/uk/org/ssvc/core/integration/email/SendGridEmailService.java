package uk.org.ssvc.core.integration.email;

import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import lombok.extern.slf4j.Slf4j;
import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.MessageType;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;
import uk.org.ssvc.core.domain.service.EmailService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static uk.org.ssvc.core.domain.model.notification.MessageType.MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY;
import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.core.domain.model.notification.SendStatus.SENT;

@Singleton
@Slf4j
public class SendGridEmailService implements EmailService {

    private static final Email SENDER = new Email("no-reply@ssvc.org.uk", "The Split Screen Van Club");
    private static final Map<MessageType, String> MESSAGE_TO_TEMPLATE_ID = new HashMap<>();
    static {
        MESSAGE_TO_TEMPLATE_ID.put(MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY, "a26c8955-a6ab-4ef3-a82e-9615979662a4");
    }

    private final SendGrid client;

    @Inject
    public SendGridEmailService(@Named("sendGrid.apiKey") String apiKey) {
        client = new SendGrid(apiKey);
    }

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        Mail mail = new Mail();

        mail.setFrom(SENDER);
        mail.addPersonalization(buildPersonalization(recipient, message));
        mail.setTemplateId(MESSAGE_TO_TEMPLATE_ID.get(message.getType()));

        log.info("Sending email recipient={} messageType={}", recipient, message.getType());

        try {
            Response response = client.api(buildRequest(mail));

            if (response.getStatusCode() != 202) {
                throw new SendGridEmailException("Failed to send email statusCode=" + response.getStatusCode());
            }

            return new NotificationSendResult(recipient, SENT, EMAIL);
        }
        catch (IOException e) {
            throw new SendGridEmailException("Failed to send email", e);
        }
    }

    private Request buildRequest(Mail mail) throws IOException {
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }

    private Personalization buildPersonalization(Recipient recipient, Message message) {
        Map<String, String> variables = message.getVariables();
        Personalization personalization = new Personalization();

        personalization.addTo(new Email(recipient.getEmailAddress(), recipient.getSalutation()));

        variables.keySet().forEach(varKey ->
            personalization.addSubstitution("{{" + varKey + "}}", variables.get(varKey)));

        return personalization;
    }

}
