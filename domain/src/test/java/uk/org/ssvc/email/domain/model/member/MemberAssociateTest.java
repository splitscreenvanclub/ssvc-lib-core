package uk.org.ssvc.email.domain.model.member;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MemberAssociateTest {

    @Test
    public void isChild_true() {
        MemberAssociate subject = new MemberAssociate("Little", "Person", LocalDate.of(2016, 6, 28));

        assertThat(subject.isChild()).isTrue();
    }

    @Test
    public void isChild_false() {
        MemberAssociate subject = new MemberAssociate("Edd", "China", LocalDate.of(1986, 6, 28));

        assertThat(subject.isChild()).isFalse();
    }

}