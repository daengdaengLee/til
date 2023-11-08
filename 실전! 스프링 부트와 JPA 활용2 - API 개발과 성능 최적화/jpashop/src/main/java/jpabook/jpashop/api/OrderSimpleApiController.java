package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * xxxToOne 관계 (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // jackson 에서 무한 루프 :  Order -(member)> Member -(orders)> Order -> ...
    // jackson 에서 lazy loading 때문에 들어가 있는 프록시 객체를 직렬화 못 함 -> Hibernate5Module 로 해결 가능하긴 한데...
    // 아니면 order 의 member.name, delivery.address 를 직접 조회해서 강제 Lazy 초기화 시킬수도 있긴 한데...
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        var orders = orderRepository.findAll(new OrderSearch());
        for (var order : orders) {
            var ignoredName = order.getMember().getName(); // 강제 Lazy 초기화
            var ignoredAddress = order.getDelivery().getAddress(); // 강제 Lazy 초기화
        }
        return orders;
    }

    // N+1 문제 : 1 + N(2) + N(2) 쿼리 실행
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        var orders = orderRepository.findAll(new OrderSearch());
        return orders.stream().map(SimpleOrderDto::new).toList();
    }

    // fetch join
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        var orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(SimpleOrderDto::new).toList();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    public static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
