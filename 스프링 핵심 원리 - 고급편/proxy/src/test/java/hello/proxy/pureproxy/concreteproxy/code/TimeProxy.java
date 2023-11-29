package hello.proxy.pureproxy.concreteproxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic {
    private final ConcreteLogic concreteLogic;

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        var startTime = System.currentTimeMillis();

        var result = concreteLogic.operation();

        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
        return result;
    }
}
