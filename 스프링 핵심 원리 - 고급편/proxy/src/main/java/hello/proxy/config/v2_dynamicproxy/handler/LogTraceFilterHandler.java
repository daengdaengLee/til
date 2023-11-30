package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {
    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 메서드 이름 필터
        var methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        try {
            var message = method.getDeclaringClass().getSimpleName() +
                    "." +
                    methodName +
                    "()";
            status = logTrace.begin(message);

            // 로직 호출
            var result = method.invoke(target, args);

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            if (status != null) {
                logTrace.exception(status, e);
            }
            throw e;
        }
    }
}
