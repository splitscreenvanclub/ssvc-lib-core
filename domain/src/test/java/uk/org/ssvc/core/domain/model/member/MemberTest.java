package uk.org.ssvc.core.domain.model.member;

import org.junit.Test;
import uk.org.ssvc.core.domain.model.notification.Contact;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    public void contacts_noAssociates() throws Exception {
        Member subject = Member.builder()
            .id("member1")
            .firstName("Ed")
            .lastName("Slocombe")
            .associates(emptySet())
            .build();

        List<Contact> actual = subject.contacts();

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getFirstName()).isEqualTo("Ed");
        assertThat(actual.get(0).getLastName()).isEqualTo("Slocombe");
    }

    @Test
    public void contacts_noChildren() throws Exception {
        Member subject = Member.builder()
            .id("member1")
            .firstName("Ed")
            .lastName("Slocombe")
            .associates(newHashSet(new MemberAssociate("My", "Wife", LocalDate.MIN)))
            .build();

        List<Contact> actual = subject.contacts();

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFirstName()).isEqualTo("Ed");
        assertThat(actual.get(0).getLastName()).isEqualTo("Slocombe");
        assertThat(actual.get(1).getFirstName()).isEqualTo("My");
        assertThat(actual.get(1).getLastName()).isEqualTo("Wife");
    }

    @Test
    public void contacts_includingChildren() throws Exception {
        Member subject = Member.builder()
            .id("member1")
            .firstName("Ed")
            .lastName("Slocombe")
            .associates(newHashSet(
                new MemberAssociate("My", "Wife", LocalDate.MIN),
                new MemberAssociate("My", "Child", LocalDate.now())))
            .build();

        List<Contact> actual = subject.contacts();

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getFirstName()).isEqualTo("Ed");
        assertThat(actual.get(0).getLastName()).isEqualTo("Slocombe");
        assertThat(actual.get(1).getFirstName()).isEqualTo("My");
        assertThat(actual.get(1).getLastName()).isEqualTo("Wife");
    }

}