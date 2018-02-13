package uk.org.ssvc.core.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MemberAssociate {

    private static final int CHILD_AGE = 18;

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;

    public boolean isChild() {
        return dateOfBirth != null &&
            dateOfBirth.isAfter(LocalDate.now().minusYears(CHILD_AGE));
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
