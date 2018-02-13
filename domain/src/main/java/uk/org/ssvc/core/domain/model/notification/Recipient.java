package uk.org.ssvc.core.domain.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AllArgsConstructor
@Getter
@Builder
@ToString(of = { "id" })
public class Recipient {

    private final String id;
    private final List<Contact> contacts;
    private final String mobileNumber;
    private final String emailAddress;

    public Recipient(String id, String firstName, String lastName,
                     String mobileNumber, String emailAddress) {
        this.id = id;
        this.contacts = newArrayList(new Contact(firstName, lastName));
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
    }

    public boolean supportsChannel(NotificationChannel channel) {
        switch (channel) {
            case EMAIL:
                return isNotBlank(emailAddress);
            case SMS:
                return isNotBlank(mobileNumber);
            default:
                return false;
        }
    }

    public String getSalutation() {
        if (contacts.size() == 1) {
            return contacts.get(0).getFirstName();
        }

        StringBuilder salutation = new StringBuilder();

        for (int i = 0; i < contacts.size(); i++) {
            salutation.append(contacts.get(i).getFirstName());

            salutation.append(i == contacts.size() - 2 ?
                " and " :
                (i == contacts.size() - 1 ? "" : ", "));
        }

        return salutation.toString();
    }

}
