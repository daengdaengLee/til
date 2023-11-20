package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void basicTest() {
        var member = new Member("member1", 10);
        memberJpaRepository.save(member);

        var findMember = memberJpaRepository.findById(member.getId()).orElseThrow();
        assertThat(findMember).isEqualTo(member);

        var result1 = memberJpaRepository.findAll();
        assertThat(result1).containsExactlyInAnyOrder(member);

        var result2 = memberJpaRepository.findByUsername("member1");
        assertThat(result2).containsExactlyInAnyOrder(member);
    }

    @Test
    void basicQuerydslTest() {
        var member = new Member("member1", 10);
        memberJpaRepository.save(member);

        var findMember = memberJpaRepository.findById(member.getId()).orElseThrow();
        assertThat(findMember).isEqualTo(member);

        var result1 = memberJpaRepository.findAll_Querydsl();
        assertThat(result1).containsExactlyInAnyOrder(member);

        var result2 = memberJpaRepository.findByUsername_Querydsl("member1");
        assertThat(result2).containsExactlyInAnyOrder(member);
    }
}