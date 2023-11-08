package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 총 주문 2개
 * - userA
 * --- JPA1 BOOK
 * --- JPA2 BOOK
 * - userB
 * --- SPRING1 BOOK
 * --- SPRING2 BOOK
 */
@RequiredArgsConstructor
@Component
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @RequiredArgsConstructor
    @Component
    @Transactional
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            var member = createMember("userA", "서울", "장군봉2길", "11111");
            em.persist(member);

            var book1 = createBook("JPA1 BOOK", 10_000, 100);
            em.persist(book1);

            var book2 = createBook("JPA2 BOOK", 20_000, 100);
            em.persist(book2);

            var orderItem1 = OrderItem.createOrderItem(book1, 10_000, 1);
            var orderItem2 = OrderItem.createOrderItem(book2, 20_000, 2);

            var delivery = createDelivery(member);
            var order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            var member = createMember("userB", "부산", "해운대길", "22222");
            em.persist(member);

            var book1 = createBook("SPRING1 BOOK", 20_000, 200);
            em.persist(book1);

            var book2 = createBook("SPRING2 BOOK", 40_000, 300);
            em.persist(book2);

            var orderItem1 = OrderItem.createOrderItem(book1, 20_000, 3);
            var orderItem2 = OrderItem.createOrderItem(book2, 40_000, 4);

            var delivery = createDelivery(member);
            var order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            var member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            var book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            var delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
