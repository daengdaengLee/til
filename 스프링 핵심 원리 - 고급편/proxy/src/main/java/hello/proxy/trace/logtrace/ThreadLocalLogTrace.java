package hello.proxy.trace.logtrace;

import hello.proxy.trace.TraceId;
import hello.proxy.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생
    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();

        var traceId = traceIdHolder.get();
        var startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void syncTraceId() {
        var traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    private void releaseTraceId() {
        var traceId = traceIdHolder.get();
        if (traceId == null) {
            return;
        }

        if (traceId.isFirstLevel()) {
            traceIdHolder.remove(); // destroy
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private void complete(TraceStatus status, Exception e) {
        var stopTimeMs = System.currentTimeMillis();
        var resultTimeMs = stopTimeMs - status.startTimeMs();
        var traceId = status.traceId();
        if (e == null) {
            log.info(
                    "[{}] {}{} time={}ms",
                    traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    status.message(),
                    resultTimeMs);
        } else {
            log.info(
                    "[{}] {}{} time={}ms ex={}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    status.message(),
                    resultTimeMs,
                    e.toString());
        }

        releaseTraceId();
    }

    private static String addSpace(String prefix, int level) {
        var sb = new StringBuilder();
        for (var i = 0; i < level; i += 1) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
