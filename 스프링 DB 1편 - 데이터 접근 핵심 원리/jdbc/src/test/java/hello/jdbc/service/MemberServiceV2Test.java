package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방시 동기화
 */
@Slf4j
@SpringBootTest // 로그를 위해
class MemberServiceV2Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void beforeEach() {
        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        this.memberRepository = new MemberRepositoryV2(dataSource);
        this.memberService = new MemberServiceV2(dataSource, this.memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        this.memberRepository.delete(MEMBER_A);
        this.memberRepository.delete(MEMBER_B);
        this.memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        // given
        var memberA = new Member(MEMBER_A, 10000);
        var memberB = new Member(MEMBER_B, 10000);
        this.memberRepository.save(memberA);
        this.memberRepository.save(memberB);

        // when
        log.info("START TX");
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);
        log.info("END TX");

        // then
        var findMemberA = memberRepository.findById(memberA.getMemberId());
        var findMemberB = memberRepository.findById(memberB.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransferEx() throws SQLException {
        // given
        var memberA = new Member(MEMBER_A, 10000);
        var memberEx = new Member(MEMBER_EX, 10000);
        this.memberRepository.save(memberA);
        this.memberRepository.save(memberEx);

        // when
        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then
        var findMemberA = memberRepository.findById(memberA.getMemberId());
        var findMemberB = memberRepository.findById(memberEx.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }
}