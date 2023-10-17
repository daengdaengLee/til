package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.MemberType;
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
            // member.setUsername("관리자");
            member.setUsername("관리자 아님");
            member.setAge(10);
            member.setType(MemberType.USER);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            var query = """
                    select nullif(m.username, '관리자') 
                    from Member as m
                    """;
            var result = em.createQuery(query, String.class)
                    .getResultList();
            for (var resultObject : result) {
                System.out.println("resultObject = " + resultObject);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}