package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // @TODO QueryDSL 로 동적 쿼리 처리
    public List<Order> findAll(OrderSearch orderSearch) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Order.class);
        var o = cq.from(Order.class);
        var m = o.join("member", JoinType.INNER); // 회원과 조인

        var criteriaList = new ArrayList<Predicate>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            var status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteriaList.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            var name = cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
            criteriaList.add(name);
        }

        cq.where(cb.and(criteriaList.toArray(new Predicate[criteriaList.size()])));
        return em.createQuery(cq)
                .setMaxResults(1000) // 최대 1000건
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                        "select o from Order o join fetch o.member m join fetch o.delivery d",
                        Order.class)
                .getResultList();
    }
}
