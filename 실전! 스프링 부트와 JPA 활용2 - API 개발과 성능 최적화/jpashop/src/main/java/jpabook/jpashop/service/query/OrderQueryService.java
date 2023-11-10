package jpabook.jpashop.service.query;

import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderQueryService {
    private final OrderRepository orderRepository;

    public List<OrderDto> ordersV2() {
        var orders = orderRepository.findAll(new OrderSearch());
        return orders.stream()
                .map(OrderDto::new)
                .toList();
    }
}
