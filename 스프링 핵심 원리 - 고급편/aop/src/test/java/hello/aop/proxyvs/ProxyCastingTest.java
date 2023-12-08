package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ProxyCastingTest {
    @Test
    void jdkProxy() {
        var target = new MemberServiceImpl();
        var factory = new ProxyFactory(target);
        factory.setProxyTargetClass(false); // JDK 동적 프록시

        // 프록시 -> 인터페이스 로 타입 캐스팅 성공
        var memberServiceProxy = (MemberService) factory.getProxy();
        log.info("memberServiceProxy Class = {}", memberServiceProxy.getClass());

        // 프록시 -> 구현 클래스 로 타입 캐스팅 실패 : ClassCastException
        assertThatThrownBy(() -> {
            var memberServiceImplProxy = (MemberServiceImpl) factory.getProxy();
            log.info("memberServiceImplProxy Class = {}", memberServiceImplProxy.getClass());
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy() {
        var target = new MemberServiceImpl();
        var factory = new ProxyFactory(target);
        factory.setProxyTargetClass(true); // CGLIB 프록시

        // 프록시 -> 인터페이스 로 타입 캐스팅 성공
        var memberServiceProxy = (MemberService) factory.getProxy();
        log.info("memberServiceProxy Class = {}", memberServiceProxy.getClass());

        // 프록시 -> 구현 클래스 로 타입 캐스팅 성공
        var memberServiceImplProxy = (MemberServiceImpl) factory.getProxy();
        log.info("memberServiceImplProxy Class = {}", memberServiceImplProxy.getClass());
    }
}
