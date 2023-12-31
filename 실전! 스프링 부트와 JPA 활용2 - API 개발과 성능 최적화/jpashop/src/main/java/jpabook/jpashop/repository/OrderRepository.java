package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;

@Repository
public class OrderRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

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

    public List<Order> findAll2(OrderSearch orderSearch) {
        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                        "select o from Order o join fetch o.member m join fetch o.delivery d",
                        Order.class)
                .getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                        "select o from Order o join fetch o.member m join fetch o.delivery d",
                        Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery("""
                        select distinct o from Order o
                        join fetch o.member m
                        join fetch o.delivery d
                        join fetch o.orderItems oi
                        join fetch oi.item i
                        """, Order.class)
                .getResultList();
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return order.status.eq(statusCond);
    }

    private BooleanExpression nameLike(String nameCond) {
        if (!StringUtils.hasText(nameCond)) {
            return null;
        }
        return member.name.like(nameCond);
    }
}
