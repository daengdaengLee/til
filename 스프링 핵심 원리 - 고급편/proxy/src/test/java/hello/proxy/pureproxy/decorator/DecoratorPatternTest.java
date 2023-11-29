package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.MessageDecorator;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.decorator.code.TimeDecorator;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    @Test
    void noDecoratorTest() {
        var component = new RealComponent();
        var client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    void decorator1() {
        var component = new RealComponent();
        var messageDecorator = new MessageDecorator(component);
        var client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    @Test
    void decorator2() {
        var component = new RealComponent();
        var messageDecorator = new MessageDecorator(component);
        var timeDecorator = new TimeDecorator(messageDecorator);
        var client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
