package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV4 {
    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        this.bizLogic(fromId, toId, money);
    }

    private void bizLogic(String fromId, String toId, int money) {
        var fromMember = this.memberRepository.findById(fromId);
        var toMember = this.memberRepository.findById(toId);

        this.memberRepository.update(fromId, fromMember.getMoney() - money);
        this.validation(toMember);
        this.memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
