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
            team.setName("TeamA");
            em.persist(team);

            var member = new Member();
            member.setName("member1");
            member.setTeamId(team.getId());
            em.persist(member);

            var findMember = em.find(Member.class, member.getId());
            var findTeamId = findMember.getTeamId();
            var findTeam = em.find(Team.class, findTeamId);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
