package hellojpa;

import hellojpa.jpql.Member;
import hellojpa.jpql.MemberDto;
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

            var result = em.createQuery("select new hellojpa.jpql.MemberDto(m.username, m.age) from Member as m", MemberDto.class)
                    .getResultList();
            var memberDto = result.get(0);
            System.out.println("memberDto.username = " + memberDto.getUsername());
            System.out.println("memberDto.age = " + memberDto.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}