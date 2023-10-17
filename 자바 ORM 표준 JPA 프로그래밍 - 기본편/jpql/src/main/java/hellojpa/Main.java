package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.MemberType;
import hellojpa.jpql.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.Arrays;

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
            // member.setType(MemberType.ADMIN);
            member.setType(MemberType.USER);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            var query = "select m.username, 'HELLO', true from Member as m where m.type = hellojpa.jpql.MemberType.ADMIN";
            var result = em.createQuery(query)
                    .getResultList();
            for (var resultObject : result) {
                var resultTuple = (Object[]) resultObject;
                System.out.println("resultTuple = " + Arrays.toString(resultTuple));
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}