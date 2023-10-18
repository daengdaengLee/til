package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try (var emf = Persistence.createEntityManagerFactory("hello")) {
            try (var em = emf.createEntityManager()) {
                logic(em);
            }
        }
    }

    private static void logic(EntityManager em) {
        var tx = em.getTransaction();
        tx.begin();

        try {
            var teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            var teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            var teamC = new Team();
            teamC.setName("팀C");
            em.persist(teamC);

            var member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            teamA.getMembers().add(member1);
            em.persist(member1);

            var member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            teamA.getMembers().add(member2);
            em.persist(member2);

            var member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            teamB.getMembers().add(member3);
            em.persist(member3);

            var member4 = new Member();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            // 엔티티 직접 사용 가능
//            var query = "select m from Member as m where m = :member";
//            var result = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getResultList();
            // 식별자 사용
//            var query = "select m from Member as m where m.id = :memberId";
//            var result = em.createQuery(query, Member.class)
//                    .setParameter("memberId", member1.getId())
//                    .getResultList();
            // 엔티티 사용
//            var query = "select m from Member as m where m.team = :team";
//            var result = em.createQuery(query, Member.class)
//                    .setParameter("team", teamA)
//                    .getResultList();
            // 식별자 사용
            var query = "select m from Member as m where m.team.id = :teamId";
            var result = em.createQuery(query, Member.class)
                    .setParameter("teamId", teamA.getId())
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (var item : result) {
                System.out.println("item = " + item);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}