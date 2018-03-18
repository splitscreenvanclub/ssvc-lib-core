package uk.org.ssvc.core.integration.sms;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsClient;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import uk.org.ssvc.core.domain.model.notification.Message;
import uk.org.ssvc.core.domain.model.notification.MessageType;
import uk.org.ssvc.core.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.core.domain.model.notification.Recipient;
import uk.org.ssvc.core.domain.service.SmsService;
import uk.org.ssvc.core.integration.template.TemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.org.ssvc.core.domain.model.notification.MessageType.*;
import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.SMS;
import static uk.org.ssvc.core.domain.model.notification.SendStatus.SENT;

@Singleton
public class NexmoSmsService implements SmsService {

    private static final String SENDER = "SSVC";
    private static final Map<MessageType, String> MESSAGE_TYPE_TO_TEMPLATE = new HashMap<>();
    static {
        MESSAGE_TYPE_TO_TEMPLATE.put(MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY, "/templates/renew-required-shortly.hbs");
        MESSAGE_TYPE_TO_TEMPLATE.put(MEMBERSHIP_DUE_FOR_RENEWAL_NOW, "/templates/renew-required-now.hbs");
        MESSAGE_TYPE_TO_TEMPLATE.put(MEMBERSHIP_LAPSED, "/templates/membership-lapsed.hbs");
    }

    private final SmsClient smsClient;
    private final TemplateRenderer templateRenderer;

    @Inject
    public NexmoSmsService(@Named("nexmo.apiKey") String apiKey,
                           @Named("nexmo.apiSecret") String secret,
                           TemplateRenderer templateRenderer) {
        this.smsClient = new NexmoClient(new TokenAuthMethod(apiKey, secret)).getSmsClient();
        this.templateRenderer = templateRenderer;
    }

    @Override
    public NotificationSendResult sendMessage(Recipient recipient, Message message) {
        try {
            String content = templateRenderer.render(MESSAGE_TYPE_TO_TEMPLATE.get(message.getType()), message.getVariables());
            SmsSubmissionResult[] responses = smsClient.submitMessage(new TextMessage(SENDER, normalisedNumber(recipient), content, true));

            for (SmsSubmissionResult response : responses) {
                if (isNotBlank(response.getErrorText())) {
                    throw new NexmoSmsException("Failed to send message recipient=" + recipient + " responses=" + Arrays.toString(responses));
                }
            }
        }
        catch (Exception e) {
            throw new NexmoSmsException("Failed to send message recipient=" + recipient, e);
        }

        return new NotificationSendResult(recipient, SENT, SMS);
    }

    private String normalisedNumber(Recipient recipient) {
        String number = recipient.getMobileNumber();

        number = number.trim().replaceAll("\\s*", "");

        if (number.startsWith("00")) {
            number = number.replaceFirst("00", "");
        }
        if (number.startsWith("0")) {
            number = "44" + number.substring(1);
        }
        if (number.startsWith("+")) {
            number = number.substring(1);
        }

        return number;
    }

}
