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
            for (var i = 1; i <= 100; i++) {
                var member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            var result = em.createQuery("select m from Member as m order by m.age desc", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(5)
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (var member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}