package jpabook.jpashop.service.springdatajpa;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.springdatajpa.MemberRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService2 {
    private final MemberRepository2 memberRepository2;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        this.validateDuplicateMember(member);
        this.memberRepository2.save(member);
        return member.getId();
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return this.memberRepository2.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return this.memberRepository2.findById(memberId).orElse(null);
    }

    @Transactional
    public void update(Long id, String name) {
        memberRepository2.findById(id).ifPresent(member -> member.setName(name));
    }

    private void validateDuplicateMember(Member member) {
        var findMembers = this.memberRepository2.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
