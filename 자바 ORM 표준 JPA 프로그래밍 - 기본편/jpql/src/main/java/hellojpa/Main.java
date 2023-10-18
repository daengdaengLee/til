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
            var teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            var teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            var teamC = new Team();
            teamC.setName("팀C");
            em.persist(teamC);

            var member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            teamA.getMembers().add(member1);
            em.persist(member1);

            var member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            teamA.getMembers().add(member2);
            em.persist(member2);

            var member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            teamB.getMembers().add(member3);
            em.persist(member3);

            var member4 = new Member();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            // N+1 문제 발생
            // var query = "select m from Member as m";
            // fetch join 으로 해결, 한 방에 쿼리
            // var query = "select m from Member as m join fetch m.team";
            // 컬렉션 fetch join
            // var query = "select t from Team as t join fetch t.members";
            // 컬렉션 fetch join 결과에서 중복 엔티티 제거
            // var query = "select distinct t from Team as t join fetch t.members";
            // 그냥 join 인 경우 SQL 은 join 이지만 select 를 Team 만 해서 결국 N+1 발생
            var query = "select t from Team as t join t.members as m";
            var result = em.createQuery(query, Team.class)
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (var item : result) {
                System.out.println("item = " + item.getName() + ", " + item.getMembers().size());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}