package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class MemberRepositoryV1Test {
    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 개본 DriverManger - 항상 새로운 커넥션 획득
//        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // 커넥션 풀링
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        repository = new MemberRepositoryV1(dataSource);
    }


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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}