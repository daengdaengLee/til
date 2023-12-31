package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void testMember() {
        var member = new Member("memberA");
        var savedMember = memberJpaRepository.save(member);

        var findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void basicCRUD() {
        var member1 = new Member("member1");
        var member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        var findMember1 = memberJpaRepository.findById(member1.getId()).orElseThrow();
        var findMember2 = memberJpaRepository.findById(member2.getId()).orElseThrow();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        var all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // 카운트 검증
        var count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        var deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("AAA", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        var result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void testNamedQuery() {
        var m1 = new Member("AAA", 10);
        var m2 = new Member("BBB", 20);
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        var result = memberJpaRepository.findByUsername("AAA");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    void paging() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));

        int age = 10;
        int offset = 1;
        int limit = 3;

        // when
        var members = memberJpaRepository.findByPage(age, offset, limit);
        var totalCount = memberJpaRepository.totalCount(age);

        // 페이지 계산 공식 적용
        // totalPage = totalCount / size ...
        // 마지막 페이지 ...
        // 최초 페이지 ...

        // then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    void bulkUpdate() {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        // when
        var resultCount = memberJpaRepository.bulkAgePlus(20);
        em.clear();

        // then
        assertThat(resultCount).isEqualTo(3);

        var members = memberJpaRepository.findAll();
        for (var member : members) {
            System.out.println("member = " + member);
        }
    }
}