package uk.org.ssvc.email.domain.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.org.ssvc.email.domain.model.notification.Message;
import uk.org.ssvc.email.domain.model.notification.MessageType;
import uk.org.ssvc.email.domain.model.notification.NotificationSendResult;
import uk.org.ssvc.email.domain.model.notification.Recipient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static uk.org.ssvc.email.domain.environment.EnvironmentRunType.D_DAY;
import static uk.org.ssvc.email.domain.model.notification.MessageType.MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY;
import static uk.org.ssvc.email.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.email.domain.model.notification.NotificationChannel.SMS;
import static uk.org.ssvc.email.domain.model.notification.SendStatus.NOT_ATTEMPTED;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    @Mock private EmailService emailService;
    @Mock private SmsService smsService;
    @Mock private Recipient recipient;
    @Mock private NotificationSendResult sendResult;

    private MessageType messageTypePreferringEmail = MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY;
    private NotificationService subject;

    @Before
    public void setUp() throws Exception {
        new SsvcRegistry(emailService, smsService, D_DAY).initialise();

        if (messageTypePreferringEmail.getPreferredChannel() != EMAIL) {
            fail();
        }

        subject = new NotificationService();
    }

    @Test
    public void usingPreferredChannel() throws Exception {
        MessageType messageType = MEMBERSHIP_DUE_FOR_RENEWAL_SHORTLY;

        if (messageType.getPreferredChannel() != EMAIL) {
            fail();
        }

        Message message = Message.builder()
            .type(messageType)
            .build();

        when(recipient.supportsChannel(EMAIL)).thenReturn(true);
        when(emailService.sendMessage(recipient, message)).thenReturn(sendResult);

        assertThat(subject.sendMessage(recipient, message)).isEqualTo(sendResult);
    }

    @Test
    public void usingNextBestChannel() throws Exception {
        Message message = Message.builder()
            .type(messageTypePreferringEmail)
            .build();

        when(recipient.supportsChannel(EMAIL)).thenReturn(false);
        when(recipient.supportsChannel(SMS)).thenReturn(true);
        when(smsService.sendMessage(recipient, message)).thenReturn(sendResult);

        assertThat(subject.sendMessage(recipient, message)).isEqualTo(sendResult);
    }

    @Test
    public void nothingWhenNoChannelSupported() throws Exception {
        Message message = Message.builder()
            .type(messageTypePreferringEmail)
            .build();

        when(recipient.supportsChannel(EMAIL)).thenReturn(false);
        when(recipient.supportsChannel(SMS)).thenReturn(false);

        NotificationSendResult result = subject.sendMessage(recipient, message);

        assertThat(result.getChannelUsed()).isNull();
        assertThat(result.getStatus()).isEqualTo(NOT_ATTEMPTED);

        verify(smsService, never()).sendMessage(recipient, message);
        verify(emailService, never()).sendMessage(recipient, message);
    }

}