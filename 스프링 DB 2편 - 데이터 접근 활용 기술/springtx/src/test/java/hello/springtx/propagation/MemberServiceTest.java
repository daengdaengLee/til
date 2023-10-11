package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * MemberService    @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON
     */
    @Test
    void outerTxOff_success() {
        // given
        var username = "outerTxOff_success";

        // when
        this.memberService.joinV1(username);

        // then : 모든 데이터가 정상 저장된다.
        assertThat(this.memberRepository.find(username).isPresent()).isTrue();
        assertThat(this.logRepository.find(username).isPresent()).isTrue();
    }

    /**
     * MemberService    @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON Exception
     */
    @Test
    void outerTxOff_fail() {
        // given
        var username = "로그예외_outerTxOff_fail";

        // when
        assertThatThrownBy(() -> this.memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        // then : 모든 데이터가 정상 저장된다.
        assertThat(this.memberRepository.find(username).isPresent()).isTrue();
        assertThat(this.logRepository.find(username).isEmpty()).isTrue();
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:OFF
     * LogRepository    @Transactional:OFF
     */
    @Test
    void singleTx() {
        // given
        var username = "singleTx";

        // when
        this.memberService.joinV1_(username);

        // then : 모든 데이터가 정상 저장된다.
        assertThat(this.memberRepository.find(username).isPresent()).isTrue();
        assertThat(this.logRepository.find(username).isPresent()).isTrue();
    }
}