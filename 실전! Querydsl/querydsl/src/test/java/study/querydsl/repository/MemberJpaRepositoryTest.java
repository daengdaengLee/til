package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

        var result = memberJpaRepository.searchByBuilder(memberSearchCondition);

        assertThat(result).extracting("username").containsExactlyInAnyOrder("member4");
    }

    @Test
    void searchTest2() {
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

        var result = memberJpaRepository.search(memberSearchCondition);

        assertThat(result).extracting("username").containsExactlyInAnyOrder("member4");
    }
}