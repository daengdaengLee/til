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

            var copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            var member2 = new Member();
            member2.setName("member2");
            member2.setWorkPeriod(new Period(LocalDateTime.now(), LocalDateTime.now()));
            member2.setHomeAddress(copyAddress);
            em.persist(member2);

            // ...

            // !!! 값 복사를 했기 때문에 안전. member1 데이터만 변경됨 !!!
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
