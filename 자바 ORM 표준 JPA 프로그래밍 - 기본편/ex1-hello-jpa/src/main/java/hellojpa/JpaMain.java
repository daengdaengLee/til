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
            var teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            var teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            var member = new Member();
            member.setName("member1");
            member.setTeam(teamA);
            em.persist(member);

            em.flush();
            em.clear();

            // 조회
            var findMember = em.find(Member.class, member.getId());
            var members = findMember.getTeam().getMembers();
            for (var m : members) {
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
