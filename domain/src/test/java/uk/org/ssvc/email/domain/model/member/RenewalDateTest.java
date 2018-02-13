package uk.org.ssvc.email.domain.model.member;

import org.junit.Test;

import java.time.LocalDate;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

public class RenewalDateTest {

    @Test
    public void dueThisYearIn_whenTrue() throws Exception {
        LocalDate now = LocalDate.now(UTC);
        RenewalDate subject = new RenewalDate(now);

        assertThat(subject.dueThisYearIn(now.getMonth())).isTrue();
    }

    @Test
    public void dueThisYearIn_whenNextYearSameMonth() throws Exception {
        LocalDate now = LocalDate.now(UTC);
        RenewalDate subject = new RenewalDate(now.plusYears(1));

        assertThat(subject.dueThisYearIn(now.getMonth())).isFalse();
    }

    @Test
    public void dueThisYearIn_whenDueNextMonthNotNow() throws Exception {
        LocalDate now = LocalDate.now(UTC);
        RenewalDate subject = new RenewalDate(now.plusMonths(1));

        assertThat(subject.dueThisYearIn(now.getMonth())).isFalse();
    }

    @Test
    public void dueThisYearIn_whenDueNextMonthAndAskingForNextMonth() throws Exception {
        LocalDate now = LocalDate.now(UTC);
        RenewalDate subject = new RenewalDate(now.plusMonths(1));

        assertThat(subject.dueThisYearIn(now.plusMonths(1).getMonth())).isTrue();
    }

    @Test
    public void willBeDueBy_true() throws Exception {
        RenewalDate subject = new RenewalDate(LocalDate.of(2018, 3, 12));

        assertThat(subject.willBeDueBy(LocalDate.of(2018, 3, 14))).isTrue();
    }

    @Test
    public void willBeDueBy_false() throws Exception {
        RenewalDate subject = new RenewalDate(LocalDate.of(2018, 3, 12));

        assertThat(subject.willBeDueBy(LocalDate.of(2018, 3, 2))).isFalse();
    }

    @Test
    public void isRenewalDue_true() throws Exception {
        RenewalDate subject = new RenewalDate(LocalDate.now().minusDays(20));

        assertThat(subject.isRenewalDue()).isTrue();
    }

    @Test
    public void isRenewalDue_false() throws Exception {
        RenewalDate subject = new RenewalDate(LocalDate.now().plusDays(20));

        assertThat(subject.isRenewalDue()).isFalse();
    }

}