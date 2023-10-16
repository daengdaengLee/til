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
            var address = new Address("city", "street", "zipcode");

            var member1 = new Member();
            member1.setName("member1");
            member1.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            member1.setHomeAddress(address);
            em.persist(member1);

            var member2 = new Member();
            member2.setName("member2");
            member2.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            member2.setHomeAddress(address);
            em.persist(member2);

            // ...

            // !!! member1, member2 모두 newCity 로 바뀜 !!!
            member1.getHomeAddress().setCity("newCity");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
