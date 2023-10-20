package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    void signIn() {
        // given
        var member = new Member();
        member.setName("bot");

        // when
        var savedId = this.memberService.join(member);

        // then
        assertThat(member).isEqualTo(this.memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복 회원 예외")
    void duplicateMemberException() {
        // given
        var member1 = new Member();
        member1.setName("bot");
        var member2 = new Member();
        member2.setName("bot");

        // when & then
        this.memberService.join(member1);
        assertThatThrownBy(() -> this.memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}