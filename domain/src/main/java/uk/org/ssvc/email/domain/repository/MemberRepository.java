package uk.org.ssvc.email.domain.repository;

import uk.org.ssvc.email.domain.model.member.Member;
import uk.org.ssvc.email.domain.model.member.search.MemberFilterCriteria;

import java.util.Collection;
import java.util.List;

public interface MemberRepository {

    Member findById(String id);

    List<Member> findByCriteria(MemberFilterCriteria filterCriteria);

    void add(Member member);

    List<Member> findAll();

    void addAll(Collection<Member> members);

}
