package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
@SpringBootTest
public class ConnectionTest {
    @Test
    void driverManager() throws SQLException {
        var con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        var con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("con = {}, class = {}", con1, con1.getClass());
        log.info("con = {}, class = {}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource - 항상 새로운 커넥션을 획득
        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        this.useDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // Connection 풀링
        var dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        this.useDataSource(dataSource);

        Thread.sleep(1000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        var con1 = dataSource.getConnection();
        var con2 = dataSource.getConnection();
        log.info("con = {}, class = {}", con1, con1.getClass());
        log.info("con = {}, class = {}", con2, con2.getClass());
    }
}
