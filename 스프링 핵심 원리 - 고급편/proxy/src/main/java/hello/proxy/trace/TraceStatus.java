package hello.proxy.trace;

public record TraceStatus(TraceId traceId, Long startTimeMs, String message) {
}
