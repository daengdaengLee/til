package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void beforeEach() {
        queryFactory = new JPAQueryFactory(em);

        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        var member1 = new Member("member1", 10, teamA);
        var member2 = new Member("member2", 20, teamA);
        em.persist(member1);
        em.persist(member2);

        var member3 = new Member("member3", 30, teamB);
        var member4 = new Member("member4", 40, teamB);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void startJpql() {
        // member1 을 찾아라.
        var jpqlString = """
                select m from Member m
                where m.username = :username
                """;

        var findMember = em.createQuery(jpqlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void startQuerydsl() {
        // var queryFactory = new JPAQueryFactory(em);
        // var m = new QMember("m"); // 같은 테이블을 조인해야 하는 경우 직접 생성해서 사용, 인자로 넘김 문자열이 alias 이름이 됨.
        // var m = QMember.member;

        var findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1")) // 파라미터 바인딩 처리
                .fetchOne();

        assertThat(findMember).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search() {
        var findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.between(10, 30)))
                .fetchOne();

        assertThat(findMember).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isGreaterThanOrEqualTo(10);
        assertThat(findMember.getAge()).isLessThanOrEqualTo(30);
    }

    @Test
    void searchAndParam() {
        var findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"),
                        member.age.eq(10))
                .fetchOne();

        assertThat(findMember).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("member1");
        assertThat(findMember.getAge()).isEqualTo(10);
    }

    @Test
    void resultFetch() {
        System.out.println("[TEST_OUTPUT] fetch");
        var fetch = queryFactory
                .selectFrom(member)
                .fetch();
        for (var fetchItem : fetch) {
            System.out.println("fetchItem = " + fetchItem);
        }

        System.out.println("[TEST_OUTPUT] fetchOne");
        var fetchOne = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne(); // 결과가 2개 이상이면 예외 발생, 없으면 null 반환.
        System.out.println("fetchOne = " + fetchOne);

        System.out.println("[TEST_OUTPUT] fetchFirst");
        var fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst(); // == limit(1).fetchOne()
        System.out.println("fetchFirst = " + fetchFirst);

        System.out.println("[TEST_OUTPUT] fetchResults (deprecated)");
        var results = queryFactory
                .selectFrom(member)
                .fetchResults(); // deprecated
        var total = results.getTotal();
        var members = results.getResults();
        System.out.println("total = " + total);
        for (var member : members) {
            System.out.println("member = " + member);
        }

        System.out.println("[TEST_OUTPUT] fetchCount (deprecated)");
        var fetchCount = queryFactory
                .selectFrom(member)
                .fetchCount();
        System.out.println("fetchCount = " + fetchCount);
    }
}
