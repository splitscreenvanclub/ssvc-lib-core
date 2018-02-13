package uk.org.ssvc.email.domain.model.notification;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.ssvc.email.domain.model.notification.NotificationChannel.EMAIL;
import static uk.org.ssvc.email.domain.model.notification.NotificationChannel.SMS;

public class RecipientTest {

    @Test
    public void supportsSms_false() throws Exception {
        Recipient subject = Recipient.builder()
            .mobileNumber("")
            .build();

        assertThat(subject.supportsChannel(SMS)).isFalse();
    }

    @Test
    public void supportsSms_true() throws Exception {
        Recipient subject = Recipient.builder()
            .mobileNumber("077987123456")
            .build();

        assertThat(subject.supportsChannel(SMS)).isTrue();
    }

    @Test
    public void supportsEmail_false() throws Exception {
        Recipient subject = Recipient.builder()
            .emailAddress("  ")
            .build();

        assertThat(subject.supportsChannel(EMAIL)).isFalse();
    }

    @Test
    public void supportsEmail_true() throws Exception {
        Recipient subject = Recipient.builder()
            .emailAddress("ed@ed.com")
            .build();

        assertThat(subject.supportsChannel(EMAIL)).isTrue();
    }

    @Test
    public void getSalutation_singleContact() throws Exception {
        Recipient subject = Recipient.builder()
            .contacts(singletonList(new Contact("Ed", "Slocombe")))
            .build();

        assertThat(subject.getSalutation()).isEqualTo("Ed");
    }

    @Test
    public void getSalutation_twoContacts() throws Exception {
        Recipient subject = Recipient.builder()
            .contacts(newArrayList(
                new Contact("Ed", "Slocombe"),
                new Contact("Wife", "Slocombe")))
            .build();

        assertThat(subject.getSalutation()).isEqualTo("Ed and Wife");
    }

    @Test
    public void getSalutation_moreContacts() throws Exception {
        Recipient subject = Recipient.builder()
            .contacts(newArrayList(
                new Contact("Ed", "Slocombe"),
                new Contact("Wife", "Slocombe"),
                new Contact("Child", "Slocombe")))
            .build();

        assertThat(subject.getSalutation()).isEqualTo("Ed, Wife and Child");
    }

}