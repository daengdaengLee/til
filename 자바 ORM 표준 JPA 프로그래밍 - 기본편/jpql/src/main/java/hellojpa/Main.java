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
            var team = new Team();
            team.setName("teamA");
            em.persist(team);

            var member = new Member();
            member.setUsername("teamA");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // var query = "select m from Member as m left join Team as t on m.username = t.name";
            var query = "select m from Member as m join Team as t on m.username = t.name";
            var result = em.createQuery(query, Member.class)
                    .getResultList();
            for (var resultMember : result) {
                System.out.println("resultMember = " + resultMember);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}