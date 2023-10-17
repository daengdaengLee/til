package hellojpa;

import hellojpa.jpql.Address;
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

            em.flush();
            em.clear();

            em.createQuery("select o.address from Order as o", Address.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}