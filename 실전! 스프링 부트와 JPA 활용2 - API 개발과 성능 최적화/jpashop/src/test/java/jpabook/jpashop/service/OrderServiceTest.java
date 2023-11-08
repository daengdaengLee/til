package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("상품 주문")
    void order() {
        // given
        var member = createMember();
        var book = createBook("시골 JPA", 10_000, 10);
        int orderCount = 2;

        // when
        var orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        var getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).as("상품 주문시 상태는 ORDER").isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).as("주문한 상품 종류 수가 정확해야 한다.").isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).as("주문 가격은 가격 * 수량이다.").isEqualTo(10_000 * orderCount);
        assertThat(book.getStockQuantity()).as("주문 수량만큼 재고가 줄아야 한다.").isEqualTo(8);
    }


    @Test
    @DisplayName("상품 주문 - 재고 수량 초과")
    void validateStockCount() {
        // given
        var member = createMember();
        var book = createBook("시골 JPA", 10_000, 10);
        var orderCount = 11;

        // when & then
        assertThatExceptionOfType(NotEnoughStockException.class)
                .as("재고 수량 부족 예외가 발생해야 한다.")
                .isThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount));
    }

    @Test
    @DisplayName("주문 취소")
    void cancel() {
        // given
        var member = createMember();
        var book = createBook("시골 JPA", 10_000, 10);
        var orderCount = 2;

        var orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        var getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).as("주문 취소시 상태는 CANCEL 이다.").isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).as("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.").isEqualTo(10);
    }

    private Member createMember() {
        var member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        var book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}