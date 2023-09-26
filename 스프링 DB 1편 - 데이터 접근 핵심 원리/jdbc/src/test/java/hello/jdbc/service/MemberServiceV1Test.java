package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 기본 동작, 트랜젝션이 없어서 문제 발생
 */
class MemberServiceV1Test {
    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void beforeEach() {
        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        this.memberRepository = new MemberRepositoryV1(dataSource);
        this.memberService = new MemberServiceV1(this.memberRepository);
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
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

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
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }
}