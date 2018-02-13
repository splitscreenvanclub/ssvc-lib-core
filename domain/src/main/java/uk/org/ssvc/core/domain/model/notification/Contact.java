package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Contact {

    private final String firstName;
    private final String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
