package uk.org.ssvc.email.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class ContactDetails {

    private final List<TelephoneNumber> telephoneNumbers;
    private final String emailAddress;

    public boolean hasMobileNumber() {
        return telephoneNumbers.stream()
            .anyMatch(no -> no.getType() == TelephoneNumber.Type.MOBILE);
    }

}
