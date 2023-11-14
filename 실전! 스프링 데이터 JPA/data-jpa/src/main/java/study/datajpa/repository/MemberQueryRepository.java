package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import java.util.List;

// 그냥 클래스를 분리하는 게 더 나은 선택일 수 있다.
// Custom Repository 같은 기능이 있다고 항상 사용해야 하는 것은 아니다.
@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final EntityManager em;

    public List<Member> findAllMembers() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
