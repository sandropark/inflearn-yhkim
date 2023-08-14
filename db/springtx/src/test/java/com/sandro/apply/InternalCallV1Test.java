package com.sandro.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
class InternalCallV1Test {

    @Autowired
    CallService callService;

    @Test
    void printProxy() throws Exception {
        log.info("class = {}", callService.getClass());
    }

    @Test
    void externalCall() throws Exception {
        callService.external();
    }

    @SpringBootApplication
    static class InternalCallV1TestConfig {
        @Bean
        public CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        InternalService internalService() {
            return new InternalService();
        }
    }

    static class ProxyService {
        protected void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}", txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly={}", readOnly);
        }
    }

    @RequiredArgsConstructor
    static class CallService extends ProxyService {

        private final InternalService internalService;

        public void external() {
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }
    }

    static class InternalService extends ProxyService {
        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }
    }
}
