package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var member1 = new Member();
            member1.setUsername("A");

            var member2 = new Member();
            member2.setUsername("B");

            var member3 = new Member();
            member3.setUsername("C");

            System.out.println("BEFORE");
            em.persist(member1); // 1, 51
            em.persist(member2); // MEM(2)
            em.persist(member3); // MEM(3)

            System.out.println("member1.id = " + member1.getId());
            System.out.println("member2.id = " + member2.getId());
            System.out.println("member3.id = " + member3.getId());
            System.out.println("AFTER");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
