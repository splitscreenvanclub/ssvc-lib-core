package uk.org.ssvc.email.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.org.ssvc.email.domain.model.user.User;

@AllArgsConstructor
@Getter
public class Note {

    private final User author;
    private final String message;

}
