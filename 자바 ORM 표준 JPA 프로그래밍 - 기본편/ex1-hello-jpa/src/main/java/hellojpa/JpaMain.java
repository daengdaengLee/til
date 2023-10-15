package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var member = new Member();
            member.setName("user1");

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==========");

            // var findMember = em.find(Member.class, member.getId());

            // var findMember = em.getReference(Member.class, member.getId());

            var findMember = em.getReference(Member.class, member.getId());
            System.out.println("fineMember: " + findMember.getClass());
            System.out.println("member.id = " + findMember.getId());
            System.out.println("member.name = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
