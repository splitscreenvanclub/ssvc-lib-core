package uk.org.ssvc.core.integration.repository;

import uk.org.ssvc.core.domain.model.member.Member;
import uk.org.ssvc.core.domain.model.member.search.MemberFilterCriteria;
import uk.org.ssvc.core.domain.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static uk.org.ssvc.core.domain.model.member.search.MemberSearchField.EXPIRY_AFTER;
import static uk.org.ssvc.core.domain.model.member.search.MemberSearchField.EXPIRY_BEFORE;

public class StubMemberRepository implements MemberRepository {

    private static StubMemberRepository instance;
    private final List<Member> members = new ArrayList<>();

    public StubMemberRepository() {
        instance = this;
    }

    public static StubMemberRepository instance() {
        return instance;
    }

    @Override
    public Member findById(String id) {
        return members.stream()
            .filter(m -> id.equals(m.getId()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Member> findByCriteria(MemberFilterCriteria filterCriteria) {
        return members.stream()
            .filter(m ->
                filterCriteria
                    .valueFor(EXPIRY_AFTER)
                    .map(minExpiry -> m.getRenewalDate().getExpiryDate().isAfter(minExpiry))
                    .orElse(true) &&
                filterCriteria
                    .valueFor(EXPIRY_BEFORE)
                    .map(maxExpiry -> m.getRenewalDate().getExpiryDate().isBefore(maxExpiry))
                    .orElse(true))
            .collect(toList());
    }

    @Override
    public void add(Member member) {
        members.add(member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members);
    }

    @Override
    public void addAll(Collection<Member> members) {
        this.members.addAll(members);
    }

    public void setMembers(Collection<Member> members) {
        clear();
        this.members.addAll(members);
    }

    public void clear() {
        members.clear();
    }

}
