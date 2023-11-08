package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return this.em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return this.em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return this.em.createQuery(
                        "select m from Member as m where m.name = :name",
                        Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
