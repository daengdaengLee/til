package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {
    @Test
    void noProxy() {
        var concreteLogic = new ConcreteLogic();
        var client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        var concreteLogic = new ConcreteLogic();
        var timeProxy = new TimeProxy(concreteLogic);
        var client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
