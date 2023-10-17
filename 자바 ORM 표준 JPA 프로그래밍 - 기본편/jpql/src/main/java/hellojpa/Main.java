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
            var member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            var query1 = em.createQuery("select m from Member as m", Member.class);
            for (var result1 : query1.getResultList()) {
                System.out.println("result1.id = " + result1.getId());
                System.out.println("result1.username = " + result1.getUsername());
                System.out.println("result1.age = " + result1.getAge());
            }

            var query2 = em.createQuery("select m.username from Member as m where m.username = :username", String.class);
            query2.setParameter("username", "member1");
            String result2 = query2.getSingleResult();
            System.out.println("result2 = " + result2);

            var query3 = em.createQuery("select m.username, m.age from Member as m");
            var result3 = query3.getResultList();
            for (var item : result3) {
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}