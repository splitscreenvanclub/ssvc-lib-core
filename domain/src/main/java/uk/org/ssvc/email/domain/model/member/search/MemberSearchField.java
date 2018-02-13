package uk.org.ssvc.email.domain.model.member.search;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Getter
public class MemberSearchField<T> {

    private static final List<MemberSearchField<?>> ALL = new ArrayList<>();

    public static final MemberSearchField<LocalDate> EXPIRY_AFTER = new MemberSearchField<>(LocalDate.class);
    public static final MemberSearchField<LocalDate> EXPIRY_BEFORE = new MemberSearchField<>(LocalDate.class);

    private Class<T> valueType;

    public MemberSearchField(Class<T> valueType) {
        this.valueType = valueType;
        ALL.add(this);
    }

    public static List<MemberSearchField> values() {
        return unmodifiableList(ALL);
    }

}
