package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
public class BasicTxTest {
    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    public static class BasicTxTestConfig {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        var status = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        this.txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        var status = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        this.txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    @Test
    void doubleCommit() {
        log.info("트랜잭션1 시작");
        var tx1 = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        this.txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        var tx2 = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋");
        this.txManager.commit(tx2);
    }

    @Test
    void doubleCommitRollback() {
        log.info("트랜잭션1 시작");
        var tx1 = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        this.txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        var tx2 = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 롤백");
        this.txManager.rollback(tx2);
    }

    @Test
    void innerCommit() {
        log.info("외부 트랜잭션 시작");
        var outer = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction());

        log.info("내부 트랜잭션 시작");
        var inner = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction() = {}", inner.isNewTransaction());

        log.info("내부 트랜재션 커밋");
        this.txManager.commit(inner);

        log.info("외부 트랜재션 커밋");
        this.txManager.commit(outer);
    }

    @Test
    void outerRollback() {
        log.info("외부 트랜잭션 시작");
        var outer = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        var inner = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜재션 커밋");
        this.txManager.commit(inner);

        log.info("외부 트랜재션 롤백");
        this.txManager.rollback(outer);
    }

    @Test
    void innerRollback() {
        log.info("외부 트랜잭션 시작");
        var outer = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        var inner = this.txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜재션 롤백");
        this.txManager.rollback(inner); // rollback-only 표시

        log.info("외부 트랜재션 커밋");
        assertThatThrownBy(() -> this.txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void innerRollbackRequiresNew() {
        log.info("외부 트랜잭션 시작");
        var outer = this.txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction() = {}", outer.isNewTransaction()); // true

        log.info("내부 트랜잭션 시작");
        var definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        var inner = this.txManager.getTransaction(definition);
        log.info("inner.isNewTransaction() = {}", inner.isNewTransaction()); // true

        log.info("내부 트랜재션 롤백");
        this.txManager.rollback(inner); // 롤백

        log.info("외부 트랜재션 커밋");
        this.txManager.commit(outer); // 커밋
    }
}
