package uk.org.ssvc.core.domain.model;

import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactDetailsTest {

    @Test
    public void primaryMobileNumber_present() throws Exception {
        ContactDetails subject = new ContactDetails(newArrayList(
                new TelephoneNumber("07798712453"),
                new TelephoneNumber("07798712456"),
                new TelephoneNumber("004412345332")),
            "");

        assertThat(subject.primaryMobileNumber().isPresent()).isTrue();
        assertThat(subject.primaryMobileNumber().get().getNumber()).isEqualTo("07798712453");
    }

    @Test
    public void hasMobile_true() throws Exception {
        ContactDetails subject = new ContactDetails(newArrayList(
                new TelephoneNumber("07798712453"),
                new TelephoneNumber("07798712456"),
                new TelephoneNumber("004412345332")),
            "");

        assertThat(subject.hasMobileNumber()).isTrue();
    }

    @Test
    public void primaryMobileNumber_notPresent() throws Exception {
        ContactDetails subject = new ContactDetails(
            newArrayList(new TelephoneNumber("004412345332")),
            "");

        assertThat(subject.primaryMobileNumber().isPresent()).isFalse();
    }

    @Test
    public void hasMobile_false() throws Exception {
        ContactDetails subject = new ContactDetails(
            newArrayList(new TelephoneNumber("004412345332")),
            "");

        assertThat(subject.hasMobileNumber()).isFalse();
    }

}