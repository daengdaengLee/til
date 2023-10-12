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
            // 여기는 필수
            member.setTeam(team);
            em.persist(member);

            // 객체지향적으로 생각하면 여기도 추가해줘야 한다.
            // 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자.
            team.getMembers().add(member);

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
