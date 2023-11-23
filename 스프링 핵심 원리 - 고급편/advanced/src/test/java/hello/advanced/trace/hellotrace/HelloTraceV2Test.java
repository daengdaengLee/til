package hello.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;

class HelloTraceV2Test {
    @Test
    void begin_end() {
        var trace = new HelloTraceV2();
        var status1 = trace.begin("hello1");
        var status2 = trace.beginSync(status1.traceId(), "hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception() {
        var trace = new HelloTraceV2();
        var status1 = trace.begin("hello1");
        var status2 = trace.beginSync(status1.traceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}