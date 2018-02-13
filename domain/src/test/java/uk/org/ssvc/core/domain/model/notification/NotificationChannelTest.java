package uk.org.ssvc.core.domain.model.notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.org.ssvc.core.domain.service.EmailService;
import uk.org.ssvc.core.domain.service.SmsService;
import uk.org.ssvc.core.domain.service.SsvcRegistry;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static uk.org.ssvc.core.domain.environment.EnvironmentRunType.DRY_RUN;
import static uk.org.ssvc.core.domain.environment.EnvironmentRunType.D_DAY;
import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.core.domain.model.notification.NotificationChannel.SMS;

@RunWith(MockitoJUnitRunner.class)
public class NotificationChannelTest {

    @Mock private EmailService emailService;
    @Mock private SmsService smsService;
    @Mock private Recipient recipient;
    @Mock private Message message;

    @Test
    public void doesnt_send_if_dry_run() throws Exception {
        new SsvcRegistry(emailService, smsService, DRY_RUN).initialise();

        SMS.sendMessage(recipient, message);

        verify(smsService, never()).sendMessage(any(), any());
        verify(emailService, never()).sendMessage(any(), any());
    }

    @Test
    public void email_calls_email_service() throws Exception {
        new SsvcRegistry(emailService, smsService, D_DAY).initialise();

        EMAIL.sendMessage(recipient, message);

        verify(emailService).sendMessage(recipient, message);
    }

    @Test
    public void sms_calls_email_service() throws Exception {
        new SsvcRegistry(emailService, smsService, D_DAY).initialise();

        SMS.sendMessage(recipient, message);

        verify(smsService).sendMessage(recipient, message);
    }

}