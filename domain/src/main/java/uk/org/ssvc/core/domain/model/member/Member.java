package uk.org.ssvc.core.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.org.ssvc.core.domain.model.Address;
import uk.org.ssvc.core.domain.model.ContactDetails;
import uk.org.ssvc.core.domain.model.Note;
import uk.org.ssvc.core.domain.model.vehicle.Vehicle;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Builder
@ToString(of = { "id", "lastName" })
public class Member {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final Set<MemberAssociate> associates;
    private final Address address;
    private final ContactDetails contactDetails;
    private final RenewalDate renewalDate;
    private final Set<MemberFlag> flags;
    private final List<Note> notes;
    private final Set<Vehicle> vehicles;
    private final Set<MemberSkill> skills;

    public Set<MemberAssociate> getChildren() {
        return associates.stream()
            .filter(MemberAssociate::isChild)
            .collect(Collectors.toSet());
    }

    public boolean hasValidMembership() {
        return !renewalDate.isRenewalDue();
    }

}
