package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManagerFactory emf;
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

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순 (desc)
     * 2. 회원 이름 오름차순 (asc)
     * 단, 2 에서 회원 이름이 없으면 마지막에 출력 (nulls last)
     */
    @Test
    void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        var members = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        var member5 = members.get(0);
        var member6 = members.get(1);
        var memberNull = members.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();

        System.out.println("[TEST_OUTPUT]");
        for (var member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    @Test
    void paging1() {
        var result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result).hasSize(2);
    }

    @Test
    void paging2() {
        var queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults(); // deprecated

        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getResults()).hasSize(2);
    }

    @Test
    void aggregation() {
        var result = queryFactory
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();

        var tuple = result.get(0);

        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    void group() {
        var result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        var teamA = result.get(0);
        var teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15); // (10 + 20) / 2

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35); // (30 + 40) / 2
    }

    /**
     * 팀 A 에 소속된 모든 회원
     */
    @Test
    void join() {
        var result = queryFactory
                .selectFrom(member)
                // .join(member.team, team)
                .leftJoin(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactlyInAnyOrder("member1", "member2");
    }

    /**
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    void thetaJoin() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        var result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactlyInAnyOrder("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA 인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    void joinOnFiltering() {
        var result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                //.join(member.team, team)
                //.where(team.name.eq("teamA")) // inner join 인 경우 on 으로 해도 되지만 where 로 해도 결과 동일
                .fetch();

        for (var tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * 연관관계 없는 엔티티 외부 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    void joinOnNoRelation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        var result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name))
                //.join(team)
                //.on(member.username.eq(team.name)) // 세타조인에서는 where 사용 x, on 사용해야 함
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();

        var findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember).isNotNull();
        var loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isFalse();
    }

    @Test
    void fetchJoinUse() {
        em.flush();
        em.clear();

        var findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember).isNotNull();
        var loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 적용").isTrue();
    }

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery() {
        var memberSub = new QMember("memberSub");

        var result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    void subQueryGoe() {
        var memberSub = new QMember("memberSub");

        var result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactlyInAnyOrder(30, 40);
    }

    /**
     * 나이가 10 초과인 회원 조회
     */
    @Test
    void subQueryIn() {
        var memberSub = new QMember("memberSub");

        var result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactlyInAnyOrder(20, 30, 40);
    }

    @Test
    void selectSubQuery() {
        var memberSub = new QMember("memberSub");

        var result = queryFactory
                .select(
                        member.username,
                        select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        for (var tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void basicCase() {
        var result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (var s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void complexCase() {
        var result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21살~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (var s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void constant() {
        var result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (var tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void concat() {
        // {username}_{age}
        var result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        for (var s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void simpleProjection() {
        var result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for (var s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void tupleProjection() {
        // Tuple 타입은 querydsl 에 종속된 타입
        // Repository 레이어 밖으로 노출하지 않는 것을 권장
        var result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (var tuple : result) {
            var username = tuple.get(member.username);
            var age = tuple.get(member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
    }

    @Test
    void findMemberDtoByJpql() {
        var result = em.createQuery("select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        for (var memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findMemberDtoBySetter() {
        var result = queryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (var memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findMemberDtoByField() {
        var result = queryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (var memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findMemberDtoByConstructor() {
        var result = queryFactory
                .select(Projections.constructor(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (var memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void findUserDtoByField() {
        var memberSub = new QMember("memberSub");
        var result = queryFactory
                .select(Projections.fields(
                        UserDto.class,
                        // 별칭 지정 (field 와 column name 이 다를 때)
                        member.username.as("name"),
                        // sub query 에 별칭 지정
                        ExpressionUtils.as(
                                JPAExpressions.select(memberSub.age.max()).from(memberSub),
                                "age")))
                .from(member)
                .fetch();

        for (var userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findUserDtoByConstructor() {
        var result = queryFactory
                // 생성자일 경우 이름이 아니라 타입만 검사
                .select(Projections.constructor(UserDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (var userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    @Test
    void findMemberDtoByQueryProjection() {
        var result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (var memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void dynamicQueryBooleanBuilder() {
        // 둘 다 없으면 where 문 자체가 없어짐
        var usernameParam = "member1";
        // String usernameParam = null;
        var ageParam = 10;
        // Integer ageParam = null;

        var result = searchMember1(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        var builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }
}
