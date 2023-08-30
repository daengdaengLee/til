package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private final Map<Long, Member> store = new HashMap<>();
    private long sequence = 0L;

    public Member save(Member member) {
        member.setId(++this.sequence);
        log.info("save: member={}", member);
        this.store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return this.store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return this.findAll()
                .stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
