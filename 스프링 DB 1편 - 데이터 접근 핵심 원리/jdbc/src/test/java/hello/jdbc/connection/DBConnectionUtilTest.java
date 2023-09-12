package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DBConnectionUtilTest {
    @Test
    void connection() {
        var connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}