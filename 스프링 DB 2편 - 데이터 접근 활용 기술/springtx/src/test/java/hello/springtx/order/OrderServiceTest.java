package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void complete() throws NotEnoughMoneyException {
        // given
        var order = new Order();
        order.setUsername("정상");

        // when
        this.orderService.order(order);

        // then
        var findOrder = this.orderRepository.findById(order.getId()).orElseThrow();
        assertThat(findOrder.getPayStatus()).isEqualTo("완료");
    }

    @Test
    void runtimeException() {
        // given
        var order = new Order();
        order.setUsername("예외");

        // when
        assertThatThrownBy(() -> this.orderService.order(order))
                .isInstanceOf(RuntimeException.class);

        // then
        var orderOptional = this.orderRepository.findById(order.getId());
        assertThat(orderOptional.isEmpty()).isTrue();
    }

    @Test
    void bizException() {
        // given
        var order = new Order();
        order.setUsername("잔고부족");

        // when
        try {
            this.orderService.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
        }

        // then
        var findOrder = this.orderRepository.findById(order.getId()).orElseThrow();
        assertThat(findOrder.getPayStatus()).isEqualTo("대기");
    }
}