package uk.org.ssvc.email.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.ssvc.email.domain.model.TelephoneNumber.Type.LANDLINE;
import static uk.org.ssvc.email.domain.model.TelephoneNumber.Type.MOBILE;

public class TelephoneNumberTest {

    @Test
    public void mobileType_07() throws Exception {
        assertThat(new TelephoneNumber("07791234556").getType()).isEqualTo(MOBILE);
    }

    @Test
    public void mobileType_00447() throws Exception {
        assertThat(new TelephoneNumber("00447791234556").getType()).isEqualTo(MOBILE);
    }

    @Test
    public void mobileType_plus447() throws Exception {
        assertThat(new TelephoneNumber("+447791234556").getType()).isEqualTo(MOBILE);
    }

    @Test
    public void otherType() throws Exception {
        assertThat(new TelephoneNumber("0182391234556").getType()).isEqualTo(LANDLINE);
    }

}