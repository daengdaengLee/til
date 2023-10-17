package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.Team;
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

            // 실무에서는 아래처럼 실제 SQL과 비슷한 모양으로 맞춰서 쓸 것 -> join 한다! 알아볼 수 있게
            // join 쿼리는 성능 예측하기 어렵기 때문
            em.createQuery("select m.team from Member as m", Team.class)
                    .getResultList();
            em.createQuery("select t from Member as m join m.team as t", Team.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}