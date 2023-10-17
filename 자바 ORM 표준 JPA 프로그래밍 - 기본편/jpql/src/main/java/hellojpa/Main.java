package hellojpa;

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

            // 엔티티 프로젝션 결과 엔티티는 모두 영속성 컨텍스트에서 관리된다.
            var result = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();

            var findMember = result.get(0);
            findMember.setAge(20);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}