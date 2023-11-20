package study.querydsl.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

@RequiredArgsConstructor
@Profile("local")
@Component
public class InitMember {
    private final InitMemberService initMemberService;

    @PostConstruct
    void init() {
        initMemberService.init();
    }

    @Component
    public static class InitMemberService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            var teamA = new Team("teamA");
            var teamB = new Team("teamB");
            em.persist(teamA);
            em.persist(teamB);

            for (var i = 0; i < 100; i += 1) {
                var selectedTeam = i % 2 == 0 ? teamA : teamB;
                em.persist(new Member("member" + i, i, selectedTeam));
            }
        }
    }
}
