package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {
    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        log.info("callService class = {}", this.callService.getClass());
    }

    @Test
    void internalCall() {
        this.callService.internal();
    }

    @Test
    void externalCall() {
        this.callService.external();
    }

    @TestConfiguration
    public static class InternalCallV1TestConfig {
        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    public static class CallService {
        public void external() {
            log.info("call external");
            this.printTxInfo();
            this.internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            this.printTxInfo();
        }

        private void printTxInfo() {
            var txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
        }
    }
}
