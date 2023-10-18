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
            var team = new Team();
            team.setName("팀A");
            em.persist(team);

            var member1 = new Member();
            member1.setUsername("관리자1");

            member1.setTeam(team);
            team.getMembers().add(member1);

            em.persist(member1);

            var member2 = new Member();
            member2.setUsername("관리자2");

            member2.setTeam(team);
            team.getMembers().add(member2);

            em.persist(member2);

            em.flush();
            em.clear();

            // 상태 필드
            // var query = "select m.username from Member as m";
            // 단일 값 연관 경로 -> 묵시적 내부 조인 발생! 탐색 O
            // var query = "select m.team from Member as m";
            // 컬렉션 값 연관 경로 -> 묵시적 내부 조인 발생! 탐색 X, 컬렉션 대상으로는 탐색할 필드가 없어서, 해도 size 정도?
            // var query = "select t.members from Team as t";
            // from 절에서 명시적 join 을 걸고 별칭을 지정해서 탐색
            var query = "select m.username from Team as t join t.members as m";
            var result = em.createQuery(query)
                    .getResultList();
            for (var resultObject : result) {
                System.out.println("resultObject = " + resultObject);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}