package hellojpa;

import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("hello");

        var em = emf.createEntityManager();

        var tx = em.getTransaction();
        tx.begin();

        try {
            // 저장
            var team = new Team();
            team.setName("TeamA");
            em.persist(team);

            var member = new Member();
            member.setName("member1");
            member.changeTeam(team);
            em.persist(member);

//            em.flush();
//            em.clear();

            var findTeam = em.find(Team.class, team.getId()); // 1차 캐시
            var members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getName());
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
