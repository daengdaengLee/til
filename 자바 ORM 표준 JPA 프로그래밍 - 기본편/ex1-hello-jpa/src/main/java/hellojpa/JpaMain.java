package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            // Criteria 사용 준비
            var cb = em.getCriteriaBuilder();
            var query = cb.createQuery(Member.class);

            var m = query.from(Member.class);

            var cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
            var result = em.createQuery(cq).getResultList();

            for (var member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
