package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void testMember() {
        var member = new Member("memberA");
        var savedMember = memberRepository.save(member);

        var findMember = memberRepository.findById(savedMember.getId()).orElseThrow();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void basicCRUD() {
        var member1 = new Member("member1");
        var member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        var findMember1 = memberRepository.findById(member1.getId()).orElseThrow();
        var findMember2 = memberRepository.findById(member2.getId()).orElseThrow();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        var all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        var count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        var deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void testNamedQuery() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var result = memberRepository.findByUsername("AAA");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    void testQuery() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var result = memberRepository.findUser("AAA", 10);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    void findUsernameList() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var result = memberRepository.findUsernameList();
        assertThat(result).containsExactlyInAnyOrder(m1.getUsername(), m2.getUsername());
    }

    @Test
    void findMemberDto() {
        var team = new Team("teamA");
        teamRepository.save(team);

        var member = new Member("AAA", 10);
        member.setTeam(team);
        memberRepository.save(member);

        var result = memberRepository.findMemberDto();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(member.getId());
        assertThat(result.get(0).getUsername()).isEqualTo(member.getUsername());
        assertThat(result.get(0).getTeamName()).isEqualTo(member.getTeam().getName());
    }

    @Test
    void findByNames() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        assertThat(result).containsExactlyInAnyOrder(m1, m2);
    }

    @Test
    void returnType() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        var findList = memberRepository.findListByUsername("AAA");
        assertThat(findList).containsExactlyInAnyOrder(m1);

        var findExistingMember = memberRepository.findMemberByUsername("AAA");
        assertThat(findExistingMember).isEqualTo(m1);

        var findNotExistingMember = memberRepository.findMemberByUsername("NNN");
        assertThat(findNotExistingMember).isNull();

        var findExistingOptional = memberRepository.findOptionalByUsername("AAA");
        assertThat(findExistingOptional.isPresent()).isTrue();
        assertThat(findExistingOptional.orElseThrow()).isEqualTo(m1);

        var findNotExistingOptional = memberRepository.findOptionalByUsername("NNN");
        assertThat(findNotExistingOptional.isEmpty()).isTrue();
    }

    @Test
    void paging() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        var pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        var page = memberRepository.findByAge(age, pageRequest);

        // then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

        for (var member : page.getContent()) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void slice() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        var pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        var slice = memberRepository.findSliceByAge(age, pageRequest);

        // then
        assertThat(slice.getContent().size()).isEqualTo(3);
        assertThat(slice.getNumber()).isEqualTo(0);
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.hasNext()).isTrue();

        for (var member : slice.getContent()) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void list() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        var pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        var list = memberRepository.findListByAge(age, pageRequest);

        // then
        assertThat(list.size()).isEqualTo(3);

        for (var member : list) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void sepCount() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        var pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        var page = memberRepository.findSepCountQuery(pageRequest);

        // then
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();

        for (var member : page.getContent()) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void mapToDto() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        var pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        var page = memberRepository.findByAge(age, pageRequest);

        var dtoPage = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        // then
        assertThat(dtoPage.getContent().size()).isEqualTo(3);
        assertThat(dtoPage.getTotalElements()).isEqualTo(5);
        assertThat(dtoPage.getNumber()).isEqualTo(0);
        assertThat(dtoPage.getTotalPages()).isEqualTo(2);
        assertThat(dtoPage.isFirst()).isTrue();
        assertThat(dtoPage.hasNext()).isTrue();

        for (var memberDto : dtoPage.getContent()) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    void bulkUpdate() {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        // when
        var resultCount = memberRepository.bulkAgePlus(20);
        // @Modifying(clearAutomatically = true) 으로 처리
        // em.clear();

        // then
        assertThat(resultCount).isEqualTo(3);

        var members = memberRepository.findAll();
        for (var member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void findMemberLazy() {
        // given
        // member1 -> teamA
        // member2 -> teamB

        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        var member1 = new Member("member1", 10, teamA);
        var member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when N + 1
        // select Member 1
        // var members = memberRepository.findAll();
        // var members = memberRepository.findMemberFetchJoin();
        // var members = memberRepository.findMemberEntityGraph();
        var members = memberRepository.findEntityGraphByUsername("member1");

        for (var member : members) {
            System.out.println("member.username = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("member.team.name = " + member.getTeam().getName());
        }
    }

    @Test
    void queryHint() {
        // given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        var findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        // 더티 체킹 안 함, 쿼리 힌트로 read only 로 설정했기 때문
        em.flush();
    }

    @Test
    void lock() {
        // given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();

        // when
        // for update 붙어서 나감
        memberRepository.findLockByUsername("member1");
    }

    @Test
    void callCustom() {
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        var members = memberRepository.findMemberCustom();

        for (var member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    void specificationBasic() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
        var members = memberRepository.findAll(spec);

        // then
        assertThat(members).hasSize(1);
        System.out.println("member = " + members.get(0));
    }

    @Test
    void queryByExample() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        // Probe
        var member = new Member("m1");
        var team = new Team("teamA");
        member.setTeam(team);
        var matcher = ExampleMatcher.matching().withIgnorePaths("age");
        var example = Example.of(member, matcher);

        var result = memberRepository.findAll(example);

        // then
        assertThat(result.get(0).getUsername()).isEqualTo("m1");
    }

    @Test
    void projections() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findProjectionsByUsername("m1");

        // then
        for (UsernameOnly usernameOnly : result) {
            System.out.println("usernameOnly = " + usernameOnly.getUsername());
        }
    }

    @Test
    void dtoProjections() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findDtoProjectionsByUsername("m1");

        // then
        for (var dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    void genericProjections() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findGenericProjectionsByUsername("m1", UsernameOnlyDto.class);

        // then
        for (var dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    void nestedClosedProjections() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findGenericProjectionsByUsername("m1", NestedClosedProjections.class);

        // then
        for (var dto : result) {
            System.out.println("dto.username = " + dto.getUsername());
            System.out.println("dto.team.name = " + dto.getTeam().getName());
        }
    }

    @Test
    void nativeQuery() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findByNativeQuery("m1");

        // then
        System.out.println("result = " + result);
    }

    @Test
    void nativeProjection() {
        // given
        var teamA = new Team("teamA");
        em.persist(teamA);

        var m1 = new Member("m1", 0, teamA);
        var m2 = new Member("m2", 0, teamA);
        em.persist(m1);
        em.persist(m2);

        em.flush();
        em.clear();

        // when
        var result = memberRepository.findByNativeProjection(PageRequest.of(0, 10));

        // then
        var content = result.getContent();
        for (var memberProjection : content) {
            System.out.println("memberProjection.id = " + memberProjection.getId());
            System.out.println("memberProjection.username = " + memberProjection.getUsername());
            System.out.println("memberProjection.teamName = " + memberProjection.getTeamName());
        }
    }
}
