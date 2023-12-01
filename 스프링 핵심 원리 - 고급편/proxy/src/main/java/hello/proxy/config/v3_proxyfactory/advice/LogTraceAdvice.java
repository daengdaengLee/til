package hello.proxy.config.v3_proxyfactory.advice;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@RequiredArgsConstructor
public class LogTraceAdvice implements MethodInterceptor {
    private final LogTrace logTrace;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceStatus status = null;
        try {
            var method = invocation.getMethod();
            var message = method.getDeclaringClass().getSimpleName() +
                    "." +
                    method.getName() +
                    "()";
            status = logTrace.begin(message);

            // 로직 호출
            var result = invocation.proceed();

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
