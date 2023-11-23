package hello.advanced.trace;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceId {
    private final String id;
    private final int level;

    public TraceId() {
        id = createId();
        level = 0;
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
