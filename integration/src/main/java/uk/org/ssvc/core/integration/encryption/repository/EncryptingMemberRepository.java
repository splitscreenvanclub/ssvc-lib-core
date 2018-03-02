package uk.org.ssvc.core.integration.encryption.repository;

import uk.org.ssvc.core.domain.model.Address;
import uk.org.ssvc.core.domain.model.ContactDetails;
import uk.org.ssvc.core.domain.model.TelephoneNumber;
import uk.org.ssvc.core.domain.model.member.Member;
import uk.org.ssvc.core.domain.model.member.MemberAssociate;
import uk.org.ssvc.core.domain.model.member.search.MemberFilterCriteria;
import uk.org.ssvc.core.domain.repository.MemberRepository;
import uk.org.ssvc.core.integration.encryption.service.EncryptionService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Singleton
public class EncryptingMemberRepository implements MemberRepository {

    private final MemberRepository delegate;
    private final EncryptionService encryptionService;

    @Inject
    public EncryptingMemberRepository(@Named("memberRepoSource") MemberRepository delegate,
                                      EncryptionService encryptionService) {
        this.delegate = delegate;
        this.encryptionService = encryptionService;
    }

    @Override
    public Member findById(String id) {
        return process(delegate.findById(id), encryptionService::decrypt);
    }

    @Override
    public List<Member> findByCriteria(MemberFilterCriteria criteria) {
        return delegate.findByCriteria(criteria).stream()
            .map(m -> process(m, encryptionService::decrypt))
            .collect(toList());
    }

    @Override
    public List<Member> findAll() {
        return delegate.findAll().stream()
            .map(m -> process(m, encryptionService::decrypt))
            .collect(toList());
    }

    @Override
    public void add(Member member) {
        delegate.add(process(member, encryptionService::encrypt));
    }

    @Override
    public void addAll(Collection<Member> members) {
        delegate.addAll(members.stream()
            .map(m -> process(m, encryptionService::encrypt))
            .collect(toList()));
    }

    private Member process(Member m, Function<String, String> fn) {
        Address address = m.getAddress();
        ContactDetails contactDetails = m.getContactDetails();
        
        return new Member(
            m.getId(),
            fn.apply(m.getFirstName()),
            fn.apply(m.getLastName()),
            m.getAssociates().stream()
                .map(a -> new MemberAssociate(
                    fn.apply(a.getFirstName()),
                    fn.apply(a.getLastName()),
                    a.getDateOfBirth()))
                .collect(toSet()),
            new Address(
                fn.apply(address.getLineOne()),
                fn.apply(address.getLineTwo()),
                fn.apply(address.getLineThree()),
                fn.apply(address.getLineFour()),
                fn.apply(address.getCounty()),
                fn.apply(address.getRegion()),
                fn.apply(address.getPostcode())
            ),
            new ContactDetails(
                contactDetails.getTelephoneNumbers().stream()
                    .map(tn -> new TelephoneNumber(fn.apply(tn.getNumber())))
                    .collect(toList()),
                fn.apply(contactDetails.getEmailAddress())
            ),
            m.getRenewalDate(),
            null,
            null,
            null,
            null
        );
    }

}
