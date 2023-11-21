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
class MemberRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void basicTest() {
        var member = new Member("member1", 10);
        memberRepository.save(member);

        var findMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(findMember).isEqualTo(member);

        var result1 = memberRepository.findAll();
        assertThat(result1).containsExactlyInAnyOrder(member);

        var result2 = memberRepository.findByUsername("member1");
        assertThat(result2).containsExactlyInAnyOrder(member);
    }
}