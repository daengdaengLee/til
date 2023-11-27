package hello.advanced.trace.strategy.code.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식
 */
@Slf4j
@RequiredArgsConstructor
public class ContextV1 {
    private final Strategy strategy;

    public void execute() {
        var startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        strategy.call(); // 위임
        // 비즈니스 로직 종료
        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
