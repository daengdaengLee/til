package hellojpa;

import hellojpa.jpql.Member;
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
            var member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            var member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            // var query ="select concat('a', 'b') from Member as m";
            // var query ="select 'a' || 'b' from Member as m";
            // var query ="select substring(m.username, 2, 3) from Member as m";
            // var query = "select locate('de', 'abcdefg') from Member as m";
            // var query = "select size(t.members) from Team as t";
            var query = "select size(t.members) from Team as t";
            var result = em.createQuery(query)
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