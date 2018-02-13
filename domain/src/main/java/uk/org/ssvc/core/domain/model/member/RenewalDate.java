package uk.org.ssvc.core.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Month;

import static java.time.ZoneOffset.UTC;

@AllArgsConstructor
@Getter
public class RenewalDate {

    public static final RenewalDate LIFETIME_MEMBERSHIP = new RenewalDate(LocalDate.now(UTC).withYear(3000));
    public static final RenewalDate POTENTIALLY_PASSED_AWAY = new RenewalDate(LocalDate.now(UTC).withYear(1000));

    private final LocalDate expiryDate;

    public boolean dueThisYearIn(Month month) {
        return expiryDate.getYear() == LocalDate.now().getYear() &&
            expiryDate.getMonth().equals(month);
    }

    public boolean willBeDueBy(LocalDate date) {
        return expiryDate.isBefore(date);
    }

    public boolean isRenewalDue() {
        return willBeDueBy(LocalDate.now(UTC));
    }

}
