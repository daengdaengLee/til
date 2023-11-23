package hello.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;

class HelloTraceV1Test {
    @Test
    void begin_end() {
        var trace = new HelloTraceV1();
        var status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() {
        var trace = new HelloTraceV1();
        var status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }
}