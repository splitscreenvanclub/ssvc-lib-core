package uk.org.ssvc.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import uk.org.ssvc.core.domain.model.TelephoneNumber.Type;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Builder
public class ContactDetails {

    private final List<TelephoneNumber> telephoneNumbers;
    private final String emailAddress;

    public boolean hasMobileNumber() {
        return primaryMobileNumber().isPresent();
    }

    public Optional<TelephoneNumber> primaryMobileNumber() {
        return telephoneNumbers.stream()
            .filter(no -> no.getType() == Type.MOBILE)
            .findFirst();
    }

}
