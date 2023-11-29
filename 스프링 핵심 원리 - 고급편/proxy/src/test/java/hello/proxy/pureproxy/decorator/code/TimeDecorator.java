package hello.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component {
    private final Component component;

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        var startTime = System.currentTimeMillis();

        var result = component.operation();

        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
        return result;
    }
}
