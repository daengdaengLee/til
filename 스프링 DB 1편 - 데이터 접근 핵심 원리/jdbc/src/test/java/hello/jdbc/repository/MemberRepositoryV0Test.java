package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        var member = new Member("memberV0", 10_000);
        var savedMember = this.repository.save(member);
        log.info("savedMember = {}", savedMember);

        // findById
        var foundMember = this.repository.findById(member.getMemberId());
        log.info("foundMember = {}", foundMember);
        assertThat(foundMember).isEqualTo(member);

        // update money: 10,000 -> 20,000
        this.repository.update(member.getMemberId(), 20_000);
        var updatedMember = this.repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20_000);

        // delete
        this.repository.delete(member.getMemberId());
        assertThatThrownBy(() -> this.repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}