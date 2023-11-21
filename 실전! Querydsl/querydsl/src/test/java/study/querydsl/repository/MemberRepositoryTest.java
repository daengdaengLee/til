package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

    @Test
    void searchTest() {
        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        var member1 = new Member("member1", 10, teamA);
        var member2 = new Member("member2", 20, teamA);
        em.persist(member1);
        em.persist(member2);

        var member3 = new Member("member3", 30, teamB);
        var member4 = new Member("member4", 40, teamB);
        em.persist(member3);
        em.persist(member4);

        var memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(35);
        memberSearchCondition.setAgeLoe(40);
        memberSearchCondition.setTeamName("teamB");

        var result = memberRepository.search(memberSearchCondition);

        assertThat(result).extracting("username").containsExactlyInAnyOrder("member4");
    }

    @Test
    void searchPageSimpleTest() {
        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        var member1 = new Member("member1", 10, teamA);
        var member2 = new Member("member2", 20, teamA);
        em.persist(member1);
        em.persist(member2);

        var member3 = new Member("member3", 30, teamB);
        var member4 = new Member("member4", 40, teamB);
        em.persist(member3);
        em.persist(member4);

        var memberSearchCondition = new MemberSearchCondition();
        var pageable = PageRequest.of(0, 3);

        var result = memberRepository.searchPageSimple(memberSearchCondition, pageable);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username")
                .containsExactly("member1", "member2", "member3");
    }

    @Test
    void searchPageComplexTest() {
        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        var member1 = new Member("member1", 10, teamA);
        var member2 = new Member("member2", 20, teamA);
        em.persist(member1);
        em.persist(member2);

        var member3 = new Member("member3", 30, teamB);
        var member4 = new Member("member4", 40, teamB);
        em.persist(member3);
        em.persist(member4);

        var memberSearchCondition = new MemberSearchCondition();
        var pageable = PageRequest.of(0, 3);

        var result = memberRepository.searchPageComplex(memberSearchCondition, pageable);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username")
                .containsExactly("member1", "member2", "member3");
    }
}