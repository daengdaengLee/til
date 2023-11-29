package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    void noProxyTest() {
        var subject = new RealSubject();
        var client = new ProxyPatternClient(subject);
        client.execute();
        client.execute();
        client.execute();
    }
}
