package uk.org.ssvc.core.domain.model.member.search;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberFilterCriteria {

    private final Map<MemberSearchField, Object> filters;

    public MemberFilterCriteria() {
        this.filters = new HashMap<>();
    }

    public MemberFilterCriteria(Map<MemberSearchField, Object> filters) {
        this.filters = new HashMap<>(filters);
    }

    public <T> MemberFilterCriteria with(MemberSearchField<T> field, T value) {
        Map<MemberSearchField, Object> newFilters = new HashMap<>(filters);
        newFilters.put(field, value);

        return new MemberFilterCriteria(newFilters);
    }

    public static MemberFilterCriteria activeMembers() {
        return new MemberFilterCriteria().with(MemberSearchField.EXPIRY_AFTER, LocalDate.now());
    }

    public <T> Optional<T> valueFor(MemberSearchField<T> field) {
        Object value = filters.get(field);

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(field.getValueType().cast(value));
    }

}
