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

        // then : log 데이터는 롤백된다.
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

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON
     */
    @Test
    void outerTxOn_success() {
        // given
        var username = "outerTxOn_success";

        // when
        this.memberService.joinV1__(username);

        // then : 모든 데이터가 정상 저장된다.
        assertThat(this.memberRepository.find(username).isPresent()).isTrue();
        assertThat(this.logRepository.find(username).isPresent()).isTrue();
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON Exception
     */
    @Test
    void outerTxOn_fail() {
        // given
        var username = "로그예외_outerTxOn_fail";

        // when
        assertThatThrownBy(() -> this.memberService.joinV1__(username))
                .isInstanceOf(RuntimeException.class);

        // then : 모든 데이터가 롤백된다.
        assertThat(this.memberRepository.find(username).isEmpty()).isTrue();
        assertThat(this.logRepository.find(username).isEmpty()).isTrue();
    }
}