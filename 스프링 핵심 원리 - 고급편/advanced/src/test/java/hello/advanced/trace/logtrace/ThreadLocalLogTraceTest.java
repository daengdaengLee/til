package hello.advanced.trace.logtrace;

import org.junit.jupiter.api.Test;

class ThreadLocalLogTraceTest {
    ThreadLocalLogTrace trace = new ThreadLocalLogTrace();

    @Test
    void begin_end_level2() {
        var status1 = trace.begin("hello1");
        var status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2() {
        var status1 = trace.begin("hello1");
        var status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}