package study.querydsl.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    EntityManager em;

    @Test
    void testEntity() {
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

        // 초기화
        em.flush();
        em.clear();

        // 확인
        var members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (var member : members) {
            System.out.println("[TEST_OUTPUT]\nmember = " + member + "\n-> member.team = " + member.getTeam());
        }
    }
}