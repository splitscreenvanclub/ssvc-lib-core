package uk.org.ssvc.core.domain.model;

import java.util.regex.Pattern;

import static uk.org.ssvc.core.domain.model.TelephoneNumber.Type.LANDLINE;
import static uk.org.ssvc.core.domain.model.TelephoneNumber.Type.MOBILE;

public class TelephoneNumber {

    private final static Pattern MOBILE_START = Pattern.compile("^(0|(\\+|00)44)7");

    private final String number;

    public TelephoneNumber(String number) {
        this.number = number.replace(" ", "");
    }

    public String getNumber() {
        return number;
    }

    public Type getType() {
        return MOBILE_START.matcher(number).find() ? MOBILE : LANDLINE;
    }

    public enum Type {
        MOBILE,
        LANDLINE
    }

}
