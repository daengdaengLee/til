package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // 타입.메서드이름 으로 먼저 named query 찾고 -> 없으면 메서드 쿼리로 수행
    // 즉, 여기서는 @Query 애노테이션 없어도 동작함
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);
}
