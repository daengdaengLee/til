package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var movie = new Movie();
            movie.setDirector("A");
            movie.setActor("B");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10_000);

            em.persist(movie);

            em.flush();
            em.clear();

            // 개별 테이블 전략에서는 이렇게 조회할 때 문제임. 복잡한 union 쿼리 실행됨.
            em.find(Item.class, movie.getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
