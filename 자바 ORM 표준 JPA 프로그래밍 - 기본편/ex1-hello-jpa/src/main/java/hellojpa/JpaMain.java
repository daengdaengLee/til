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

            em.find(Movie.class, movie.getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
