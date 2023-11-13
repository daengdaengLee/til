package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
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
}