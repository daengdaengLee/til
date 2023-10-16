package hellojpa;

import jakarta.persistence.Persistence;

import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var member = new Member();
            member.setName("user1");
            member.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            member.setHomeAddress(new Address("city", "street", "zipcode"));

            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
