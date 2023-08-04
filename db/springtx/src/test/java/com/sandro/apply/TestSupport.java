package com.sandro.apply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TestSupport {

    @SpringBootApplication
    static class Context {}

    @Service
    static class BasicService {
        @Transactional
        public boolean tx() {
            log.info("call tx");
            return TransactionSynchronizationManager.isActualTransactionActive();
        }

        public boolean nonTx() {
            log.info("call nonTx");
            return TransactionSynchronizationManager.isActualTransactionActive();
        }
    }
}
