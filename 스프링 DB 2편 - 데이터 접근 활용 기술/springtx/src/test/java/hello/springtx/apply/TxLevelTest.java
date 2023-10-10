package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class TxLevelTest {
    @Autowired
    LevelService service;

    @Test
    void orderTest() {
        this.service.write();
        this.service.read();
    }

    @TestConfiguration
    public static class TxLevelTestConfig {
        @Bean
        public LevelService levelService() {
            return new LevelService();
        }
    }

    @Slf4j
    @Transactional(readOnly = true)
    public static class LevelService {
        // @Transactional(readOnly = false) // readOnly = false 가 기본값
        @Transactional
        public void write() {
            log.info("write");
            this.printTxInfo();
        }

        public void read() {
            log.info("read");
            this.printTxInfo();
        }

        private void printTxInfo() {
            var txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);

            var readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("read only = {}", readOnly);
        }
    }
}
