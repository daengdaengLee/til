package hellojpa;

import hellojpa.jpql.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

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

            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member as m")
                    .getResultList();
            var result = resultList.get(0);
            System.out.println("result[0] (username) = " + result[0]);
            System.out.println("result[1] (age) = " + result[1]);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}