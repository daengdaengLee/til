package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.decorator.code.RealComponent;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {
    @Test
    void noDecoratorTest() {
        var component = new RealComponent();
        var client = new DecoratorPatternClient(component);
        client.execute();
    }
}
