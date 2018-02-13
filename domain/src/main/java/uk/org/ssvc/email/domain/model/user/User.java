package uk.org.ssvc.email.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@Getter
@ToString(of = { "id", "firstName", "lastName" })
public class User {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final Set<UserRole> roles;

}
