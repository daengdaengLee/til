package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            var team = new Team();
            team.setName("team1");
            em.persist(team);

            var member = new Member();
            member.setName("user1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==========");

            var findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getClass());
            System.out.println("findMember.team = " + findMember.getTeam().getClass());

            System.out.println("==========");
            System.out.println("findMember.team.name: " + findMember.getTeam().getName());
            System.out.println("==========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
